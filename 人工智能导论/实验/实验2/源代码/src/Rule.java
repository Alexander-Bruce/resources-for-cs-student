import java.util.Set;

/**
 * 代表专家系统中的一个 IF-THEN 规则。
 * 每个规则包含一个ID、一组条件（IF 部分）、
 * 一个结论（THEN 部分）和一个描述。
 */
public class Rule {
    private final String id; // 规则的唯一标识符
    private final Set<String> conditions; // 规则触发所需满足的条件事实集合 (IF 部分)
    private final String conclusion;      // 规则触发后得出的结论事实 (THEN 部分)
    private final String description;     // 对规则的人类可读描述

    /**
     * Rule 类的构造函数。
     * @param id 规则的唯一标识符 (例如: "R1").
     * @param conditions 前提条件事实的集合 (IF 部分)。
     * @param conclusion 结果事实 (THEN 部分)。
     * @param description 对规则的文本描述。
     */
    public Rule(String id, Set<String> conditions, String conclusion, String description) {
        this.id = id;
        // 注意：如果需要，确保 conditions 是不可变的或防御性拷贝。
        // 在此示例中，假设 Set 不会被外部修改，直接使用是可行的。
        this.conditions = conditions;
        this.conclusion = conclusion;
        this.description = description;
    }

    // --- Getters (获取器方法) ---

    public String getId() {
        return id;
    }

    public Set<String> getConditions() {
        return conditions;
    }

    public String getConclusion() {
        return conclusion;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 检查在给定的当前知识库下，此规则的所有条件是否都已满足。
     * @param currentFacts 当前已知的事实集合。
     * @return 如果 currentFacts 包含了此规则的所有条件，则返回 true，否则返回 false。
     */
    public boolean conditionsMet(Set<String> currentFacts) {
        // 检查当前事实集合是否包含此规则要求的所有条件
        return currentFacts.containsAll(this.conditions);
    }

    @Override
    public String toString() {
        // 主要用于调试，保留英文键名，但值是变量内容
        return "规则{" +
                "id='" + id + '\'' +
                ", 条件=" + conditions +
                ", 结论='" + conclusion + '\'' +
                '}';
    }
}