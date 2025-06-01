import java.util.*;

/**
 * 一个使用正向推理的简单林业专家系统。
 * 它根据观察到的症状诊断潜在的树木问题。
 */
public class ForestryExpertSystem {

    private final List<Rule> ruleBase;          // 规则库，包含所有规则
    private final Set<String> knowledgeBase;    // 知识库，存储当前已知的事实
    private final List<String> firedRulesLog;   // 记录已触发规则的ID列表（按顺序）

    /**
     * 构造函数：初始化规则库和知识库。
     */
    public ForestryExpertSystem() {
        this.ruleBase = initializeRuleBase();
        this.knowledgeBase = new HashSet<>();
        this.firedRulesLog = new ArrayList<>();
    }

    /**
     * 初始化专家系统的规则集合。
     * @return 包含所有已定义规则的列表。
     */
    private List<Rule> initializeRuleBase() {
        List<Rule> rules = new ArrayList<>();

        // --- 定义规则 (按要求至少10条) ---
        // 注意：规则的描述已经是中文
        rules.add(new Rule("R1", Set.of("has_yellow_leaves", "has_stunted_growth"), "suspect_nutrient_deficiency", "如果树叶发黄且生长迟缓，则怀疑是营养缺乏。"));
        rules.add(new Rule("R2", Set.of("has_brown_spots", "is_humid"), "suspect_fungal_issue", "如果树叶有褐色斑点且环境潮湿，则怀疑是真菌问题。"));
        rules.add(new Rule("R3", Set.of("has_sticky_leaves", "sees_ants"), "suspect_aphids", "如果树叶发粘且看到蚂蚁，则怀疑是蚜虫侵害。"));
        rules.add(new Rule("R4", Set.of("has_wilted_leaves", "soil_is_dry"), "suspect_drought_stress", "如果树叶枯萎且土壤干燥，则怀疑是干旱胁迫。"));
        rules.add(new Rule("R5", Set.of("has_holes_in_bark", "sees_sawdust"), "suspect_borers", "如果树皮有孔洞且看到锯末，则怀疑是蛀干害虫。"));
        rules.add(new Rule("R6", Set.of("sees_mushrooms_at_base", "soil_is_wet"), "suspect_root_rot", "如果在树基部看到蘑菇且土壤潮湿，则怀疑是根腐病。"));
        rules.add(new Rule("R7", Set.of("has_white_powder_on_leaves"), "suspect_powdery_mildew", "如果树叶上有白色粉末，则怀疑是白粉病。"));
        rules.add(new Rule("R8", Set.of("suspect_fungal_issue"), "recommend_fungicide", "如果怀疑是真菌问题，则建议使用杀菌剂。"));
        rules.add(new Rule("R9", Set.of("suspect_aphids"), "recommend_insecticidal_soap", "如果怀疑是蚜虫侵害，则建议使用杀虫皂。"));
        rules.add(new Rule("R10", Set.of("suspect_borers"), "recommend_professional_consultation", "如果怀疑是蛀干害虫，则建议寻求专业咨询。"));
        rules.add(new Rule("R11", Set.of("suspect_nutrient_deficiency"), "recommend_fertilizer", "如果怀疑是营养缺乏，则建议施肥。"));
        rules.add(new Rule("R12", Set.of("suspect_drought_stress"), "recommend_watering", "如果怀疑是干旱胁迫，则建议浇水。"));
        rules.add(new Rule("R13", Set.of("suspect_root_rot"), "recommend_improving_drainage", "如果怀疑是根腐病，则建议改善排水。"));
        rules.add(new Rule("R14", Set.of("suspect_powdery_mildew"), "recommend_fungicide_or_neem_oil", "如果怀疑是白粉病，则建议使用杀菌剂或印楝油。"));
        return rules;
    }

    /**
     * 正向推理引擎。
     * 持续将规则应用于知识库，直到没有新的事实可以被推导出来。
     * @param initialFacts 用户提供的初始事实集合。
     */
    public void forwardChain(Set<String> initialFacts) {
        // 如果多次运行，清除之前的状态
        this.knowledgeBase.clear();
        this.firedRulesLog.clear();

        // 将初始事实添加到知识库
        this.knowledgeBase.addAll(initialFacts);

        System.out.println("\n--- 推理过程开始 ---");
        System.out.println("初始知识库: " + sortedSet(this.knowledgeBase));

        boolean newFactAdded = true; // 标志位，跟踪一轮推理中是否有新事实加入

        // 只要有新事实被加入，就持续迭代
        while (newFactAdded) {
            newFactAdded = false; // 为当前这轮推理重置标志位

            // 遍历规则库中的所有规则
            for (Rule rule : this.ruleBase) {
                // 检查规则条件是否被当前知识库满足
                // 并且 规则的结论尚未存在于知识库中
                if (rule.conditionsMet(this.knowledgeBase) && !this.knowledgeBase.contains(rule.getConclusion())) {

                    // 触发规则：将结论添加到知识库
                    this.knowledgeBase.add(rule.getConclusion());
                    this.firedRulesLog.add(rule.getId()); // 记录被触发规则的ID
                    newFactAdded = true; // 表明有新事实被加入

                    // 打印关于被触发规则的信息
                    System.out.println("规则 " + rule.getId() + " 被触发: " + rule.getConditions() + " => " + rule.getConclusion());
                    System.out.println("当前知识库: " + sortedSet(this.knowledgeBase));

                    // 可选：在此处中断并从头开始重新检查规则（有时效率更高）。
                    // 为简单起见，我们完成当前这轮的检查。
                }
            }
            // 完成一轮对所有规则的检查
            if(newFactAdded) {
                System.out.println("--- 完成一轮推理，继续检查是否有新规则可触发 ---");
            }
        }

        System.out.println("--- 推理过程结束 (没有更多规则可以触发) ---");
    }

    /**
     * 显示推理过程的结果。
     * @param initialFacts 用户提供的初始事实。
     */
    public void displayResults(Set<String> initialFacts) {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("             推理结果");
        System.out.println("=".repeat(40));

        // --- 显示初始事实 ---
        System.out.println("\n--- 初始事实 ---");
        if (initialFacts.isEmpty()) {
            System.out.println("无初始事实输入。");
        } else {
            for (String fact : sortedSet(initialFacts)) {
                System.out.println("- " + fact);
            }
        }

        // --- 计算并显示推导出的事实 ---
        Set<String> deducedFacts = new HashSet<>(this.knowledgeBase);
        deducedFacts.removeAll(initialFacts); // 仅保留不是初始提供的事实

        System.out.println("\n--- 推导出的结论 ---");
        if (deducedFacts.isEmpty()) {
            System.out.println("未能根据初始事实和规则推导出新的结论。");
        } else {
            for (String fact : sortedSet(deducedFacts)) {
                System.out.println("- " + fact);
            }
        }

        // --- 显示被触发的规则 ---
        System.out.println("\n--- 使用的规则序列 ---");
        if (this.firedRulesLog.isEmpty()) {
            System.out.println("没有规则被触发。");
        } else {
            for (String ruleId : this.firedRulesLog) {
                // 通过ID查找规则描述
                String description = "未知的规则描述"; // 默认值
                for (Rule rule : this.ruleBase) {
                    if (rule.getId().equals(ruleId)) {
                        description = rule.getDescription();
                        break;
                    }
                }
                System.out.println("- " + ruleId + ": " + description);
            }
        }

        System.out.println("\n" + "=".repeat(40));
        System.out.println("           系统运行结束");
        System.out.println("=".repeat(40));
    }

    /**
     * 辅助方法：从 Set 获取一个排序后的 List，用于一致性地显示。
     * @param factSet 事实集合。
     * @return 一个排序后的事实列表。
     */
    private List<String> sortedSet(Set<String> factSet) {
        List<String> sortedList = new ArrayList<>(factSet);
        Collections.sort(sortedList);
        return sortedList;
    }

    /**
     * 主方法：运行专家系统，处理用户输入。
     */
    public static void main(String[] args) {
        ForestryExpertSystem expertSystem = new ForestryExpertSystem();
        Set<String> initialFacts = new HashSet<>();

        // 使用 try-with-resources 确保 Scanner 被自动关闭
        try (Scanner scanner = new Scanner(System.in, "UTF-8")) { // 指定UTF-8编码以支持中文输入/输出
            System.out.println("=".repeat(40));
            System.out.println("  欢迎使用简易林业专家系统 (树木问题诊断)");
            System.out.println("=".repeat(40));

            // 定义并向用户展示可能的初始事实
            Set<String> possibleFacts = Set.of(
                    "has_yellow_leaves", "has_stunted_growth", "has_brown_spots",
                    "is_humid", "has_sticky_leaves", "sees_ants", "has_wilted_leaves",
                    "soil_is_dry", "has_holes_in_bark", "sees_sawdust",
                    "sees_mushrooms_at_base", "soil_is_wet", "has_white_powder_on_leaves"
            );

            System.out.println("\n请输入观察到的初始症状/事实。");
            System.out.println("从以下建议列表中选择，或输入自定义事实。");
            System.out.println("每个事实占一行，输入完成后直接按 Enter 键结束输入。");
            System.out.println("-".repeat(20));
            // 按字母顺序打印可能的示例子事实
            List<String> sortedPossibleFacts = new ArrayList<>(possibleFacts);
            Collections.sort(sortedPossibleFacts);
            for(String fact : sortedPossibleFacts) {
                System.out.println("- " + fact);
            }
            System.out.println("-".repeat(20));


            while (true) {
                System.out.print("输入事实 (或直接按 Enter 结束): ");
                String input = scanner.nextLine().trim().toLowerCase(); // 读取行，去除首尾空格，转为小写

                if (input.isEmpty()) {
                    if (initialFacts.isEmpty()) {
                        System.out.println("错误：您尚未输入任何初始事实。请至少输入一个。");
                        continue; // 重新要求输入
                    } else {
                        break; // 用户完成输入
                    }
                }

                // 基本验证：检查是否包含空格（可选，但对于简单事实是良好实践）
                if (input.contains(" ")) {
                    System.out.println("警告：输入包含空格，建议使用下划线连接单词 (例如 'has_yellow_leaves')。");
                    // 可以选择替换空格为下划线，或者按原样添加
                    // input = input.replace(" ", "_");
                }

                // 添加事实（即使它包含空格或不是预定义的）
                if (initialFacts.add(input)) {
                    System.out.println(" -> 已添加事实: " + input);
                } else {
                    System.out.println(" -> 事实 '" + input + "' 已存在。");
                }

            } // 输入循环结束

            System.out.println("\n--- 输入的初始事实 ---");
            if (!initialFacts.isEmpty()) {
                for (String fact : expertSystem.sortedSet(initialFacts)) {
                    System.out.println("- " + fact);
                }
            } else {
                // 这个情况理论上在循环中的检查后不会到达
                System.out.println("未提供初始事实。");
                return; // 如果由于某种原因没有输入事实，则退出
            }


            // 运行推理引擎
            expertSystem.forwardChain(initialFacts);

            // 显示结果
            expertSystem.displayResults(initialFacts);

        } catch (Exception e) {
            // 通用异常处理，增强健壮性
            System.err.println("\n发生意外错误: " + e.getMessage());
            e.printStackTrace(); // 打印堆栈跟踪信息，用于调试
        }
    }
}