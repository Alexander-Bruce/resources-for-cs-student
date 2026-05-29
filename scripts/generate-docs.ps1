$ErrorActionPreference = 'Stop'

$Root = (Resolve-Path (Join-Path $PSScriptRoot '..')).Path
$DocsDir = Join-Path $Root 'docs'
$IgnoredTopDirectories = @('.git', '.github', 'docs', 'scripts')
$IgnoredDirectoryNames = @('__pycache__', 'target', 'build', 'out', 'bin', '.idea', '.vscode', '.settings', '.vs', 'node_modules', 'dist', 'coverage')
$IgnoredFileNames = @('.DS_Store', 'Thumbs.db', 'desktop.ini', '.classpath', '.project', '.factorypath', '.qmake.stash')
$IgnoredExtensions = @('.pyc', '.pyo', '.pyd', '.class', '.iml', '.iws', '.ipr', '.o', '.obj', '.exe', '.dll', '.so', '.dylib', '.log', '.tmp', '.temp', '.user', '.suo')

function Convert-ToUrlPath {
    param([Parameter(Mandatory = $true)][string]$Path)

    $EncodedSegments = $Path.Split('/') | ForEach-Object {
        [System.Uri]::EscapeDataString($_)
    }
    return [string]::Join('/', $EncodedSegments)
}

function Format-FileSize {
    param([Parameter(Mandatory = $true)][long]$Bytes)

    if ($Bytes -ge 1GB) { return ('{0:N2} GB' -f ($Bytes / 1GB)) }
    if ($Bytes -ge 1MB) { return ('{0:N2} MB' -f ($Bytes / 1MB)) }
    if ($Bytes -ge 1KB) { return ('{0:N1} KB' -f ($Bytes / 1KB)) }
    return "$Bytes B"
}

function Escape-MarkdownCell {
    param([Parameter(Mandatory = $true)][string]$Text)

    return $Text.Replace('|', '\|')
}

function Get-RelativePath {
    param([Parameter(Mandatory = $true)][string]$FullName)

    return $FullName.Substring($Root.Length + 1).Replace('\', '/')
}

function Test-IsIgnoredRelativePath {
    param([Parameter(Mandatory = $true)][string]$RelativePath)

    $Segments = $RelativePath.Split('/')
    foreach ($Segment in $Segments) {
        if ($IgnoredDirectoryNames -contains $Segment) {
            return $true
        }
    }

    $FileName = [System.IO.Path]::GetFileName($RelativePath)
    if ($IgnoredFileNames -contains $FileName) {
        return $true
    }

    $Extension = [System.IO.Path]::GetExtension($RelativePath).ToLowerInvariant()
    return $IgnoredExtensions -contains $Extension
}

if (Test-Path -LiteralPath $DocsDir) {
    Remove-Item -LiteralPath $DocsDir -Recurse -Force
}
New-Item -ItemType Directory -Path $DocsDir | Out-Null

$Modules = Get-ChildItem -LiteralPath $Root -Force -Directory |
    Where-Object { $IgnoredTopDirectories -notcontains $_.Name } |
    Sort-Object Name

foreach ($Module in $Modules) {
    $ModuleName = $Module.Name
    $DocPath = Join-Path $DocsDir "$ModuleName.md"
    $TopDirectories = Get-ChildItem -LiteralPath $Module.FullName -Force -Directory |
        Where-Object { -not (Test-IsIgnoredRelativePath (Get-RelativePath $_.FullName)) } |
        Sort-Object Name
    $AllDirectories = Get-ChildItem -LiteralPath $Module.FullName -Force -Recurse -Directory |
        Where-Object { -not (Test-IsIgnoredRelativePath (Get-RelativePath $_.FullName)) } |
        Sort-Object FullName
    $AllFiles = Get-ChildItem -LiteralPath $Module.FullName -Force -Recurse -File |
        Where-Object { -not (Test-IsIgnoredRelativePath (Get-RelativePath $_.FullName)) } |
        Sort-Object FullName
    $Lines = [System.Collections.Generic.List[string]]::new()

    $Lines.Add("# $ModuleName")
    $Lines.Add('')
    $Lines.Add("[返回总览](../README.md) | [打开目录](../$(Convert-ToUrlPath $ModuleName)/)")
    $Lines.Add('')
    $Lines.Add('## 概览')
    $Lines.Add('')
    $Lines.Add("- 目录数：$($AllDirectories.Count)")
    $Lines.Add("- 文件数：$($AllFiles.Count)")
    if ($TopDirectories.Count -gt 0) {
        $TopDirectoryLinks = $TopDirectories | ForEach-Object {
            "[$($_.Name)](../$(Convert-ToUrlPath "$ModuleName/$($_.Name)")/)"
        }
        $Lines.Add("- 一级结构：$($TopDirectoryLinks -join '、')")
    } else {
        $Lines.Add('- 一级结构：无子目录')
    }

    $Lines.Add('')
    $Lines.Add('## 目录结构')
    $Lines.Add('')
    if ($TopDirectories.Count -eq 0) {
        $Lines.Add('- 无子目录')
    } else {
        foreach ($Directory in $TopDirectories) {
            $RelativePath = Get-RelativePath $Directory.FullName
            $SubDirectoryCount = (Get-ChildItem -LiteralPath $Directory.FullName -Force -Recurse -Directory |
                Where-Object { -not (Test-IsIgnoredRelativePath (Get-RelativePath $_.FullName)) } |
                Measure-Object).Count
            $SubFileCount = (Get-ChildItem -LiteralPath $Directory.FullName -Force -Recurse -File |
                Where-Object { -not (Test-IsIgnoredRelativePath (Get-RelativePath $_.FullName)) } |
                Measure-Object).Count
            $Lines.Add("- [$RelativePath](../$(Convert-ToUrlPath $RelativePath)/)（$SubDirectoryCount 个子目录，$SubFileCount 个文件）")
        }
    }

    $Lines.Add('')
    $Lines.Add('## 文件索引')
    $Lines.Add('')
    if ($AllFiles.Count -eq 0) {
        $Lines.Add('暂无文件。')
    } else {
        $Lines.Add('| 路径 | 大小 |')
        $Lines.Add('| --- | ---: |')
        foreach ($File in $AllFiles) {
            $RelativePath = Get-RelativePath $File.FullName
            $LinkText = Escape-MarkdownCell $RelativePath
            $UrlPath = Convert-ToUrlPath $RelativePath
            $Lines.Add("| [$LinkText](../$UrlPath) | $(Format-FileSize $File.Length) |")
        }
    }

    Set-Content -LiteralPath $DocPath -Value $Lines -Encoding UTF8
}

$TotalFiles = (Get-ChildItem -LiteralPath $Root -Force -Recurse -File |
    Where-Object {
        $_.FullName -notlike (Join-Path $Root '.git\*') -and
        $_.FullName -notlike (Join-Path $DocsDir '*') -and
        -not (Test-IsIgnoredRelativePath (Get-RelativePath $_.FullName))
    } |
    Measure-Object).Count
$TotalDirectories = (Get-ChildItem -LiteralPath $Root -Force -Recurse -Directory |
    Where-Object {
        $_.FullName -notlike (Join-Path $Root '.git\*') -and
        $_.FullName -ne $DocsDir -and
        $_.FullName -notlike (Join-Path $DocsDir '*') -and
        -not (Test-IsIgnoredRelativePath (Get-RelativePath $_.FullName))
    } |
    Measure-Object).Count

$Readme = [System.Collections.Generic.List[string]]::new()
$Readme.Add('# 计算机专业课程资料库')
$Readme.Add('')
$Readme.Add('本仓库整理了计算机相关课程的课程资料、实验、课程设计、习题与作业，适合作为课程复习、实验参考和项目归档索引使用。')
$Readme.Add('')
$Readme.Add('## 快速导航')
$Readme.Add('')
$Readme.Add('| 模块 | 详细索引 | 目录入口 | 主要内容 |')
$Readme.Add('| --- | --- | --- | --- |')
foreach ($Module in $Modules) {
    $TopDirectories = Get-ChildItem -LiteralPath $Module.FullName -Force -Directory |
        Where-Object { -not (Test-IsIgnoredRelativePath (Get-RelativePath $_.FullName)) } |
        Sort-Object Name
    if ($TopDirectories.Count -gt 0) {
        $Summary = ($TopDirectories | ForEach-Object { Escape-MarkdownCell $_.Name }) -join '、'
    } else {
        $Summary = '文件资料'
    }
    $DocPath = Convert-ToUrlPath "docs/$($Module.Name).md"
    $ModulePath = Convert-ToUrlPath $Module.Name
    $Readme.Add("| $(Escape-MarkdownCell $Module.Name) | [查看文件索引]($DocPath) | [打开目录]($ModulePath/) | $Summary |")
}
$Readme.Add('')
$Readme.Add('## 仓库结构')
$Readme.Add('')
$Readme.Add("当前仓库共整理 $($Modules.Count) 个顶层模块，约 $TotalDirectories 个目录、$TotalFiles 个文件。详细文件清单已按模块拆分到 `docs/`，每份索引都精确到具体文件。")
$Readme.Add('')
$Readme.Add('- `docs/`：按课程生成的详细文件索引。')
$Readme.Add('- `scripts/generate-docs.ps1`：重新扫描仓库并生成 README 与 docs 索引。')
$Readme.Add('- `.gitignore`：统一忽略系统缓存、IDE 配置、编译产物和本地工具生成目录。')
$Readme.Add('- `LICENSE`：MIT 许可证。')
$Readme.Add('')
$Readme.Add('## 使用建议')
$Readme.Add('')
$Readme.Add('- 查找某门课资料时，先在上方表格进入对应的详细索引。')
$Readme.Add('- 查看实验、课程设计或源码时，优先从对应模块的 `实验`、`课程设计`、`源代码` 目录进入。')
$Readme.Add('- 仓库已清理常见生成文件，源码类项目如需运行，请在本地按课程目录重新编译或安装依赖。')
$Readme.Add('')
$Readme.Add('## 维护方式')
$Readme.Add('')
$Readme.Add('新增、删除或移动资料后，可在 PowerShell 中运行以下命令刷新索引：')
$Readme.Add('')
$Readme.Add('```powershell')
$Readme.Add('./scripts/generate-docs.ps1')
$Readme.Add('```')
$Readme.Add('')
$Readme.Add('## 许可证')
$Readme.Add('')
$Readme.Add('本仓库采用 [MIT License](LICENSE) 开源。资料仅供学习交流使用，请遵守课程与学校相关要求。')
$Readme.Add('')

Set-Content -LiteralPath (Join-Path $Root 'README.md') -Value $Readme -Encoding UTF8

Write-Host "已生成 $($Modules.Count) 个课程索引。"