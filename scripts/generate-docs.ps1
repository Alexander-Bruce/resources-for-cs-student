$ErrorActionPreference = 'Stop'

$Root = (Resolve-Path (Join-Path $PSScriptRoot '..')).Path
$DocsDir = Join-Path $Root 'docs'
$IgnoredTopDirectories = @('.git', '.github', 'docs', 'scripts')
$IgnoredDirectoryNames = @('__pycache__', 'target', 'build', 'out', 'bin', '.idea', '.vscode', '.settings', '.vs', 'node_modules', 'dist', 'coverage')
$IgnoredFileNames = @('.DS_Store', 'Thumbs.db', 'desktop.ini', '.classpath', '.project', '.factorypath', '.qmake.stash')
$IgnoredExtensions = @('.pyc', '.pyo', '.pyd', '.class', '.iml', '.iws', '.ipr', '.o', '.obj', '.exe', '.dll', '.so', '.dylib', '.log', '.tmp', '.temp', '.user', '.suo')
$CourseOverviews = @{
    'C++程序设计' = 'C++程序设计模块整理面向对象程序设计、语言语法、课堂示例和课程设计源码，适合复习 C++ 基础语法、类与对象、工程实践和课程项目实现。'
    'C语言程序设计' = 'C语言程序设计模块收录 C 语言基础、课程设计和源码材料，适合查找结构化程序设计、指针、数组、文件操作等入门实践内容。'
    'Javaweb' = 'Javaweb 模块围绕 Servlet、JSP、JDBC、MyBatis、Spring 与 SpringMVC 等 Web 后端技术，整理实验源码、报告和课程资料。'
    'Java程序设计' = 'Java程序设计模块整理 Java 基础语法、面向对象、图形界面、网络通信和课程设计源码，适合复习 Java 编程与项目实践。'
    'Linux基础' = 'Linux基础模块收录 Linux 命令、实验、作业和课程资料，适合回顾 Shell、文件系统、权限管理和基础运维操作。'
    'Python基础' = 'Python基础模块整理 Python 入门练习、答案、大作业和源码，适合查找语法练习、脚本实践和课程项目材料。'
    'Web程序设计' = 'Web程序设计模块收录前端与 Web 应用课程设计源码，适合查看页面结构、交互实现和 Web 项目组织方式。'
    '人工智能导论' = '人工智能导论模块整理 AI 基础实验、课程资料和结课论文，适合查找搜索、推理、机器学习入门等课程实践内容。'
    '操作系统' = '操作系统模块收录实验、课程设计和课程资料，覆盖进程管理、内存管理、文件系统与系统调用等核心主题。'
    '数据库' = '数据库模块整理数据库课程资料、习题、实验和课程设计源码，适合复习 SQL、关系模型、事务和数据库应用开发。'
    '数据结构' = '数据结构模块整理课程资料、实验、习题和课程设计，适合复习线性表、树、图、查找、排序等基础结构与算法实现。'
    '机器学习' = '机器学习模块收录课程资料、实验报告、实验源码和习题，适合查找模型训练、数据处理和实验复现材料。'
    '算法' = '算法模块聚合算法设计与分析的课程资料、实验题目和课程设计源码，重点覆盖搜索、动态规划、贪心、字符串、复杂度和高级数据结构等内容。'
    '编译原理' = '编译原理模块整理课程设计、词法语法分析资料和相关源码，适合查看手工实现、Lex/YACC 实现和课设报告。'
    '计算机体系结构' = '计算机体系结构模块收录体系结构实验材料，适合复习指令流水线、存储层次和处理器结构等内容。'
    '计算机组成原理' = '计算机组成原理模块整理实验、课程设计和源码，适合查看数字逻辑、CPU 组成、指令系统和仿真实现。'
    '计算机网络' = '计算机网络模块收录实验、作业和课程设计，适合复习协议分层、网络编程、路由、抓包分析和综合项目。'
    '课设' = '课设模块用于集中归档跨课程或综合类课程设计材料，适合快速定位独立项目和最终提交内容。'
    '软件工程' = '软件工程模块整理课程资料、实验和习题，适合复习需求分析、设计建模、测试、项目管理和软件过程相关内容。'
    '选修课' = '选修课模块归档通识与兴趣类课程资料，按课程主题拆分，适合查找非专业核心课的学习材料。'
    '高等数学' = '高等数学模块整理数学教材和复习资料，适合查找微积分、级数、空间解析几何等基础数学内容。'
}

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

function Get-CourseOverview {
    param([Parameter(Mandatory = $true)][string]$ModuleName)

    if ($CourseOverviews.ContainsKey($ModuleName)) {
        return $CourseOverviews[$ModuleName]
    }

    return "本模块整理了 $ModuleName 相关资料，适合按课程主题查找课件、实验、作业、课程设计和源码。"
}

function Get-DirectoryPurpose {
    param(
        [Parameter(Mandatory = $true)][string]$DirectoryName,
        [Parameter(Mandatory = $true)][string]$ModuleName
    )

    switch -Regex ($DirectoryName) {
        '^课程资料$' { return '课程资料通常包含课件、讲义、示例代码和参考材料，适合先用来建立知识框架或考前复习。' }
        '^实验$' { return '实验部分通常包含实验报告、题目、源码、数据或运行素材，适合复现实验流程和查看具体实现。' }
        '^课程设计$' { return '课程设计部分通常包含完整项目、设计文档、报告和源码，适合查看从需求到实现的综合实践成果。' }
        '^习题(\+答案)?$' { return '习题部分用于整理练习题、答案、截图或解析材料，适合按知识点查漏补缺。' }
        '^作业$' { return '作业部分收录阶段性课程作业和提交材料，适合查看课堂练习与对应实现。' }
        '^源代码$' { return '源代码目录集中放置可阅读或可运行的项目代码，适合直接进入工程查看实现细节。' }
        '^大作业$' { return '大作业目录归档综合性作业或项目源码，适合查看较完整的课程实践成果。' }
        '^结课论文$' { return '结课论文目录收录课程论文与最终提交材料，适合了解课程主题的总结性成果。' }
        '^同济-下册$' { return '同济-下册目录整理高等数学下册相关资料，适合复习多元函数、级数、空间解析几何等内容。' }
        '^task\d+$' { return 'task 目录对应课程设计或综合实践的阶段任务，适合按任务编号查看要求、实现和提交内容。' }
        default {
            if ($DirectoryName -match '艺术|鉴赏|细胞') {
                return '该目录对应一门选修课程主题，主要归档课堂资料、作业或结课材料。'
            }

            return "该分区收录 $ModuleName 中与 $DirectoryName 相关的资料，可进入目录查看具体文件。"
        }
    }
}

function Get-VisibleDirectories {
    param([Parameter(Mandatory = $true)][string]$DirectoryPath)

    return @(Get-ChildItem -LiteralPath $DirectoryPath -Force -Directory |
        Where-Object { -not (Test-IsIgnoredRelativePath (Get-RelativePath $_.FullName)) } |
        Sort-Object Name)
}

function Get-VisibleFiles {
    param(
        [Parameter(Mandatory = $true)][string]$DirectoryPath,
        [switch]$Recurse
    )

    if ($Recurse) {
        return @(Get-ChildItem -LiteralPath $DirectoryPath -Force -Recurse -File |
            Where-Object { -not (Test-IsIgnoredRelativePath (Get-RelativePath $_.FullName)) } |
            Sort-Object FullName)
    }

    return @(Get-ChildItem -LiteralPath $DirectoryPath -Force -File |
        Where-Object { -not (Test-IsIgnoredRelativePath (Get-RelativePath $_.FullName)) } |
        Sort-Object Name)
}

function Get-ContentSignals {
    param([System.IO.FileInfo[]]$Files)

    $Files = @($Files | Where-Object { $null -ne $_ })
    $Extensions = @($Files | ForEach-Object { $_.Extension.ToLowerInvariant() } | Sort-Object -Unique)
    $LowerNames = @($Files | ForEach-Object { $_.Name.ToLowerInvariant() })
    $Signals = [System.Collections.Generic.List[string]]::new()

    if ($LowerNames | Where-Object { $_ -match '报告|report' }) { $Signals.Add('实验/课程报告') | Out-Null }
    if ($LowerNames | Where-Object { $_ -match '题目|problem' }) { $Signals.Add('题目材料') | Out-Null }
    if ($LowerNames | Where-Object { $_ -match '答案|answer|solution' }) { $Signals.Add('答案或解析') | Out-Null }
    if ($Extensions -contains '.ppt' -or $Extensions -contains '.pptx') { $Signals.Add('课件演示文稿') | Out-Null }
    if ($Extensions -contains '.pdf') { $Signals.Add('PDF 资料') | Out-Null }
    if ($Extensions -contains '.doc' -or $Extensions -contains '.docx') { $Signals.Add('Word 文档') | Out-Null }
    if ($Extensions -contains '.java') { $Signals.Add('Java 源码') | Out-Null }
    if ($Extensions -contains '.c' -or $Extensions -contains '.cpp' -or $Extensions -contains '.h' -or $Extensions -contains '.hpp') { $Signals.Add('C/C++ 源码') | Out-Null }
    if ($Extensions -contains '.py') { $Signals.Add('Python 源码') | Out-Null }
    if ($Extensions -contains '.html' -or $Extensions -contains '.jsp' -or $Extensions -contains '.css' -or $Extensions -contains '.js') { $Signals.Add('Web 页面与脚本') | Out-Null }
    if ($Extensions -contains '.sql') { $Signals.Add('SQL 脚本') | Out-Null }
    if ($Extensions -contains '.md') { $Signals.Add('Markdown 说明') | Out-Null }
    if ($Extensions -contains '.txt') { $Signals.Add('文本题目或记录') | Out-Null }
    if ($Extensions -contains '.png' -or $Extensions -contains '.jpg' -or $Extensions -contains '.jpeg') { $Signals.Add('截图或图片素材') | Out-Null }
    if ($LowerNames -contains 'pom.xml' -or $LowerNames -contains 'mvnw' -or $LowerNames -contains 'mvnw.cmd') { $Signals.Add('Maven 工程配置') | Out-Null }
    if ($LowerNames -contains 'dockerfile' -or $LowerNames -contains 'docker-compose.yml') { $Signals.Add('容器化配置') | Out-Null }
    if ($LowerNames -contains 'package.json') { $Signals.Add('Node/Web 工程配置') | Out-Null }
    if ($LowerNames -contains 'makefile' -or $LowerNames -contains 'cmakelists.txt') { $Signals.Add('本地构建脚本') | Out-Null }

    if ($Signals.Count -eq 0) {
        $Signals.Add('课程资料文件') | Out-Null
    }

    return $Signals.ToArray()
}

function Get-FilePurpose {
    param(
        [Parameter(Mandatory = $true)][System.IO.FileInfo]$File,
        [Parameter(Mandatory = $true)][string]$RelativePath
    )

    $Name = $File.Name
    $LowerName = $Name.ToLowerInvariant()
    $Extension = $File.Extension.ToLowerInvariant()

    if ($LowerName -eq 'readme.md') { return '项目说明与使用入口' }
    if ($LowerName -eq 'readme.zh-cn.md') { return '中文项目说明' }
    if ($LowerName -eq 'license') { return '许可证文件' }
    if ($LowerName -eq 'pom.xml') { return 'Maven 依赖与构建配置' }
    if ($LowerName -eq 'package.json') { return 'Node/Web 项目配置' }
    if ($LowerName -eq 'dockerfile') { return '容器镜像构建配置' }
    if ($LowerName -eq 'docker-compose.yml') { return '容器编排配置' }
    if ($LowerName -eq 'mvnw') { return 'macOS/Linux Maven Wrapper' }
    if ($LowerName -eq 'mvnw.cmd') { return 'Windows Maven Wrapper' }
    if ($LowerName -eq 'run.sh') { return 'macOS/Linux 运行脚本' }
    if ($LowerName -eq 'run.ps1') { return 'Windows PowerShell 运行脚本' }
    if ($LowerName -eq 'makefile') { return '本地编译脚本' }
    if ($LowerName -eq 'cmakelists.txt') { return 'CMake 构建配置' }
    if ($LowerName -match '^main\.(java|c|cpp|py)$') { return '程序入口' }
    if ($LowerName -match '^test\.(java|c|cpp|py)$' -or $LowerName -match '(^|[_.-])test([_.-]|$)') { return '测试或自检代码' }
    if ($Name -match '实验报告|报告|report') { return '实验/课程报告' }

    $PreferExtensionPurpose = @('.java', '.c', '.cpp', '.h', '.hpp', '.py', '.ppt', '.pptx') -contains $Extension
    if (-not $PreferExtensionPurpose) {
        if ($Name -match '题目|problem') { return '题目说明' }
        if ($Name -match '答案|answer|solution') { return '答案或解析' }
    }

    switch ($Extension) {
        '.java' { return 'Java 源码' }
        '.c' { return 'C 源码' }
        '.cpp' { return 'C++ 源码' }
        '.h' { return 'C/C++ 头文件' }
        '.hpp' { return 'C++ 头文件' }
        '.py' { return 'Python 脚本' }
        '.jsp' { return 'JSP 页面' }
        '.html' { return 'HTML 页面' }
        '.css' { return '样式文件' }
        '.js' { return 'JavaScript 脚本' }
        '.sql' { return '数据库脚本' }
        '.md' { return 'Markdown 文档' }
        '.txt' { return '文本资料' }
        '.pdf' { return 'PDF 资料' }
        '.ppt' { return '课程课件' }
        '.pptx' { return '课程课件' }
        '.doc' { return 'Word 文档' }
        '.docx' { return 'Word 文档' }
        '.png' { return '截图或图片资料' }
        '.jpg' { return '图片资料' }
        '.jpeg' { return '图片资料' }
        '.xml' { return 'XML 配置或数据文件' }
        '.yml' { return 'YAML 配置文件' }
        '.yaml' { return 'YAML 配置文件' }
        default { return '课程资料文件' }
    }
}

function Get-DirectoryComment {
    param(
        [Parameter(Mandatory = $true)][System.IO.DirectoryInfo]$Directory,
        [Parameter(Mandatory = $true)][string]$ModuleName
    )

    $Name = $Directory.Name
    switch -Regex ($Name) {
        '^课程资料$' { return '课件、讲义与参考资料' }
        '^实验$' { return '实验报告、题目与实现材料' }
        '^实验(\d+)$' { return "第 $($Matches[1]) 次实验材料" }
        '^课程设计$' { return '综合课程项目与报告' }
        '^源代码\+分报告$' { return '源码与分阶段报告' }
        '^习题(\+答案)?$' { return '练习题与答案解析' }
        '^作业$' { return '课程作业提交材料' }
        '^源代码$' { return '项目源代码入口' }
        '^experiment\d*$' { return '实验工程目录' }
        '^exp\d+$' { return '阶段实验材料' }
        '^Connect_six$' { return '六子棋课程设计源码' }
        '^src$' { return '源码根目录' }
        '^main$' { return '主程序代码' }
        '^test$' { return '测试代码' }
        '^java$' { return 'Java 包源码' }
        '^webapp$' { return 'Web 页面与部署资源' }
        '^WEB-INF$' { return 'Java Web 部署配置' }
        '^resources?$' { return '资源与配置文件' }
        '^images?$' { return '图片素材' }
        '^evaluation$' { return '评估、评分或分析逻辑' }
        '^variables?$' { return '数据结构和值对象' }
        '^tools?$|^utils?$' { return '工具类与辅助逻辑' }
        '^chapter\d+' { return '章节课程资料' }
        '^task\d+$' { return '课程设计阶段任务' }
        '^同济-下册$' { return '高等数学下册资料' }
        default {
            $Signals = @(Get-ContentSignals -Files (Get-VisibleFiles -DirectoryPath $Directory.FullName -Recurse))
            if ($Signals.Count -gt 0 -and $Signals[0] -ne '课程资料文件') {
                return $Signals[0]
            }

            return "$ModuleName 相关资料"
        }
    }
}

function Format-TreeEntry {
    param(
        [Parameter(Mandatory = $true)][string]$Name,
        [Parameter(Mandatory = $true)][bool]$IsDirectory,
        [Parameter(Mandatory = $true)][string]$Comment
    )

    $Label = $Name
    if ($IsDirectory) {
        $Label = "$Name/"
    }

    if ([string]::IsNullOrWhiteSpace($Comment)) {
        return $Label
    }

    $PadSize = [Math]::Max(1, 34 - $Label.Length)
    return "$Label$(' ' * $PadSize)# $Comment"
}

function Add-TreeChildren {
    param(
        [Parameter(Mandatory = $true)][string]$DirectoryPath,
        [Parameter(Mandatory = $true)][string]$ModuleName,
        [Parameter(Mandatory = $true)][AllowEmptyString()][string]$Prefix,
        [Parameter(Mandatory = $true)][int]$Depth,
        [Parameter(Mandatory = $true)][int]$MaxDepth,
        [Parameter(Mandatory = $true)][int]$MaxEntriesPerDirectory,
        [Parameter(Mandatory = $true)][System.Collections.Generic.List[string]]$Lines
    )

    if ($Depth -gt $MaxDepth) {
        return
    }

    $Directories = @(Get-VisibleDirectories -DirectoryPath $DirectoryPath)
    $Files = @(Get-VisibleFiles -DirectoryPath $DirectoryPath)
    $Entries = [System.Collections.Generic.List[object]]::new()

    foreach ($Directory in $Directories) {
        $Entries.Add([pscustomobject]@{ Name = $Directory.Name; FullName = $Directory.FullName; IsDirectory = $true; Item = $Directory }) | Out-Null
    }
    foreach ($File in $Files) {
        $Entries.Add([pscustomobject]@{ Name = $File.Name; FullName = $File.FullName; IsDirectory = $false; Item = $File }) | Out-Null
    }

    $VisibleEntries = @($Entries | Select-Object -First $MaxEntriesPerDirectory)
    $HasMore = $Entries.Count -gt $VisibleEntries.Count

    for ($Index = 0; $Index -lt $VisibleEntries.Count; $Index++) {
        $Entry = $VisibleEntries[$Index]
        $IsLast = ($Index -eq ($VisibleEntries.Count - 1)) -and (-not $HasMore)
        $Connector = '├── '
        if ($IsLast) {
            $Connector = '└── '
        }

        $RelativePath = Get-RelativePath $Entry.FullName
        if ($Entry.IsDirectory) {
            $Comment = Get-DirectoryComment -Directory $Entry.Item -ModuleName $ModuleName
        } else {
            $Comment = Get-FilePurpose -File $Entry.Item -RelativePath $RelativePath
        }

        $Lines.Add($Prefix + $Connector + (Format-TreeEntry -Name $Entry.Name -IsDirectory $Entry.IsDirectory -Comment $Comment)) | Out-Null

        if ($Entry.IsDirectory -and $Depth -lt $MaxDepth) {
            $ChildPrefix = $Prefix + '│   '
            if ($IsLast) {
                $ChildPrefix = $Prefix + '    '
            }
            Add-TreeChildren -DirectoryPath $Entry.FullName -ModuleName $ModuleName -Prefix $ChildPrefix -Depth ($Depth + 1) -MaxDepth $MaxDepth -MaxEntriesPerDirectory $MaxEntriesPerDirectory -Lines $Lines
        }
    }

    if ($HasMore) {
        $Remaining = $Entries.Count - $VisibleEntries.Count
        $Lines.Add($Prefix + "└── ...                              # 还有 $Remaining 项未展开") | Out-Null
    }
}

function Get-TreeLines {
    param(
        [Parameter(Mandatory = $true)][string]$DirectoryPath,
        [Parameter(Mandatory = $true)][string]$ModuleName,
        [int]$MaxDepth = 3,
        [int]$MaxEntriesPerDirectory = 12
    )

    $Lines = [System.Collections.Generic.List[string]]::new()
    $Lines.Add('.') | Out-Null
    Add-TreeChildren -DirectoryPath $DirectoryPath -ModuleName $ModuleName -Prefix '' -Depth 1 -MaxDepth $MaxDepth -MaxEntriesPerDirectory $MaxEntriesPerDirectory -Lines $Lines
    return $Lines.ToArray()
}

function Get-RepresentativeFiles {
    param(
        [System.IO.FileInfo[]]$Files,
        [int]$Limit = 8
    )

    $Files = @($Files | Where-Object { $null -ne $_ })
    $Patterns = @(
        'README.md',
        'README.zh-CN.md',
        '*实验报告*',
        '*报告*',
        '*题目*',
        '*problem*',
        '*答案*',
        '*answer*',
        'pom.xml',
        'package.json',
        'Dockerfile',
        'docker-compose.yml',
        'run.*',
        'Main.*',
        'Test.*',
        '*.pdf',
        '*.ppt',
        '*.pptx'
    )
    $Selected = [System.Collections.Generic.List[System.IO.FileInfo]]::new()
    $Seen = @{}

    foreach ($Pattern in $Patterns) {
        foreach ($File in ($Files | Where-Object { $_.Name -like $Pattern } | Sort-Object FullName)) {
            if (-not $Seen.ContainsKey($File.FullName)) {
                $Selected.Add($File) | Out-Null
                $Seen[$File.FullName] = $true
                if ($Selected.Count -ge $Limit) { return $Selected.ToArray() }
            }
        }
    }

    foreach ($File in ($Files | Sort-Object FullName)) {
        if (-not $Seen.ContainsKey($File.FullName)) {
            $Selected.Add($File) | Out-Null
            $Seen[$File.FullName] = $true
            if ($Selected.Count -ge $Limit) { return $Selected.ToArray() }
        }
    }

    return $Selected.ToArray()
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
    $TopDirectories = @(Get-VisibleDirectories -DirectoryPath $Module.FullName)
    $AllDirectories = Get-ChildItem -LiteralPath $Module.FullName -Force -Recurse -Directory |
        Where-Object { -not (Test-IsIgnoredRelativePath (Get-RelativePath $_.FullName)) } |
        Sort-Object FullName
    $AllFiles = @(Get-VisibleFiles -DirectoryPath $Module.FullName -Recurse)
    $Lines = [System.Collections.Generic.List[string]]::new()

    $Lines.Add("# $ModuleName")
    $Lines.Add('')
    $Lines.Add("[返回总览](../README.md) | [打开目录](../$(Convert-ToUrlPath $ModuleName)/)")
    $Lines.Add('')
    $Lines.Add('## 概览')
    $Lines.Add('')
    $Lines.Add((Get-CourseOverview -ModuleName $ModuleName))
    $Lines.Add('')
    $Lines.Add("当前模块包含 $($AllDirectories.Count) 个目录、$($AllFiles.Count) 个文件。建议先看目录结构，再从下方模块导览进入对应部分。")

    $Lines.Add('')
    $Lines.Add('## 目录结构')
    $Lines.Add('')
    $Lines.Add('```text')
    foreach ($TreeLine in (Get-TreeLines -DirectoryPath $Module.FullName -ModuleName $ModuleName -MaxDepth 3 -MaxEntriesPerDirectory 12)) {
        $Lines.Add($TreeLine)
    }
    $Lines.Add('```')

    $Lines.Add('')
    $Lines.Add('## 模块导览')
    $Lines.Add('')
    if ($TopDirectories.Count -eq 0) {
        $Lines.Add("该模块没有继续拆分子目录，资料直接放在 [$ModuleName](../$(Convert-ToUrlPath $ModuleName)/) 目录下。")
        $RootSignals = @(Get-ContentSignals -Files $AllFiles)
        $Lines.Add('')
        $Lines.Add("- 内容识别：$($RootSignals -join '、')")
        $RepresentativeFiles = @(Get-RepresentativeFiles -Files $AllFiles -Limit 8)
        if ($RepresentativeFiles.Count -gt 0) {
            $Lines.Add('- 代表文件：')
            foreach ($File in $RepresentativeFiles) {
                $RelativePath = Get-RelativePath $File.FullName
                $UrlPath = Convert-ToUrlPath $RelativePath
                $Purpose = Get-FilePurpose -File $File -RelativePath $RelativePath
                $Lines.Add("  - [$RelativePath](../$UrlPath)：$Purpose")
            }
        }
    } else {
        foreach ($Directory in $TopDirectories) {
            $RelativePath = Get-RelativePath $Directory.FullName
            $SubDirectories = @(Get-ChildItem -LiteralPath $Directory.FullName -Force -Recurse -Directory |
                Where-Object { -not (Test-IsIgnoredRelativePath (Get-RelativePath $_.FullName)) } |
                Sort-Object FullName)
            $SubFiles = @(Get-VisibleFiles -DirectoryPath $Directory.FullName -Recurse)
            $SubDirectoryCount = $SubDirectories.Count
            $SubFileCount = $SubFiles.Count
            $ChildDirectories = @(Get-VisibleDirectories -DirectoryPath $Directory.FullName)
            $EntryLinks = $ChildDirectories | Select-Object -First 8 | ForEach-Object {
                $ChildRelativePath = Get-RelativePath $_.FullName
                "[$($_.Name)](../$(Convert-ToUrlPath $ChildRelativePath)/)"
            }
            $Signals = @(Get-ContentSignals -Files $SubFiles)
            $RepresentativeFiles = @(Get-RepresentativeFiles -Files $SubFiles -Limit 8)

            $Lines.Add("### [$($Directory.Name)](../$(Convert-ToUrlPath $RelativePath)/)")
            $Lines.Add('')
            $Lines.Add('```text')
            foreach ($TreeLine in (Get-TreeLines -DirectoryPath $Directory.FullName -ModuleName $ModuleName -MaxDepth 3 -MaxEntriesPerDirectory 10)) {
                $Lines.Add($TreeLine)
            }
            $Lines.Add('```')
            $Lines.Add('')
            $Lines.Add((Get-DirectoryPurpose -DirectoryName $Directory.Name -ModuleName $ModuleName))
            $Lines.Add('')
            $Lines.Add("- 内容规模：$SubDirectoryCount 个子目录，$SubFileCount 个文件")
            $Lines.Add("- 内容识别：$($Signals -join '、')")
            if ($EntryLinks.Count -gt 0) {
                $EntryLine = "- 主要入口：$($EntryLinks -join '、')"
                if ($ChildDirectories.Count -gt $EntryLinks.Count) {
                    $EntryLine += ' 等'
                }
                $Lines.Add($EntryLine)
            } else {
                $Lines.Add("- 主要入口：[$($Directory.Name)](../$(Convert-ToUrlPath $RelativePath)/)")
            }
            if ($RepresentativeFiles.Count -gt 0) {
                $Lines.Add('- 代表文件：')
                foreach ($File in $RepresentativeFiles) {
                    $FileRelativePath = Get-RelativePath $File.FullName
                    $FileUrlPath = Convert-ToUrlPath $FileRelativePath
                    $Purpose = Get-FilePurpose -File $File -RelativePath $FileRelativePath
                    $Lines.Add("  - [$FileRelativePath](../$FileUrlPath)：$Purpose")
                }
            }
            $Lines.Add('')
        }
    }

    while ($Lines.Count -gt 0 -and $Lines[$Lines.Count - 1] -eq '') {
        $Lines.RemoveAt($Lines.Count - 1)
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
$Readme.Add('| 模块 | 课程导览 | 目录入口 | 主要内容 |')
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
    $Readme.Add("| $(Escape-MarkdownCell $Module.Name) | [查看导览]($DocPath) | [打开目录]($ModulePath/) | $Summary |")
}
$Readme.Add('')
$Readme.Add('## 仓库结构')
$Readme.Add('')
$Readme.Add("当前仓库共整理 $($Modules.Count) 个顶层模块，约 $TotalDirectories 个目录、$TotalFiles 个文件。课程导览已按模块拆分到 ``docs/``，每份页面都会先用代码块目录树展示结构，再说明各部分内容、入口链接和代表文件。")
$Readme.Add('')
$Readme.Add('- `docs/`：按课程生成的导览页，包含带注释目录树、模块说明、入口链接和代表文件。')
$Readme.Add('- `scripts/generate-docs.ps1`：重新扫描仓库并生成 README 与 docs 索引。')
$Readme.Add('- `.gitignore`：统一忽略系统缓存、IDE 配置、编译产物和本地工具生成目录。')
$Readme.Add('- `LICENSE`：MIT 许可证。')
$Readme.Add('')
$Readme.Add('## 使用建议')
$Readme.Add('')
$Readme.Add('- 查找某门课资料时，先在上方表格进入对应的课程导览。')
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