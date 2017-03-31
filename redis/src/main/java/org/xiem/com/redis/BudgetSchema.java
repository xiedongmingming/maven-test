package org.xiem.com;

public class BudgetSchema {// REDIS中存放的数据

    private static final char KeyDividerChar = ':';

    static final String KeyDivider = Character.toString(KeyDividerChar);

    static String getCompoundKey(String baseKey, long id) {
        return new StringBuilder().append(baseKey).append(KeyDividerChar).append(id).toString();
    }

    static final String CampaignBudgetLimitTable = "ad:budget:limit:campaign";
    static final String LineBudgetLimitTable = "ad:budget:limit:line";
    static final String TacticBudgetLimitTable = "ad:budget:limit:tactic";

    static final String CampaignBudgetClaimTable = "ad:budget:claim:campaign";
    static final String LineBudgetClaimTable = "ad:budget:claim:line";
    static final String TacticBudgetClaimTable = "ad:budget:claim:tactic";
    static final String BudgetClaimResetKey = "ad:budget:claim:reset";

    static final String CampaignBudgetUsedTable = "ad:budget:used:campaign";
    static final String LineBudgetUsedTable = "ad:budget:used:line";
    static final String TacticBudgetUsedTable = "ad:budget:used:tactic";
    static final String TacticBudgetUsedBucketTable = "ad:budget:used:tactic:bucket";

    static final String CampaignImpressionLimitTable = "ad:impression:limit:campaign";
    static final String LineImpressionLimitTable = "ad:impression:limit:line";
    static final String TacticImpressionLimitTable = "ad:impression:limit:tactic";

    static final String CampaignImpressionClaimTable = "ad:impression:claim:campaign";
    static final String LineImpressionClaimTable = "ad:impression:claim:line";
    static final String TacticImpressionClaimTable = "ad:impression:claim:tactic";
    static final String ImpressionClaimResetKey = "ad:impression:claim:reset";

    static final String CampaignImpressionUsedTable = "ad:impression:used:campaign";
    static final String LineImpressionUsedTable = "ad:impression:used:line";
    static final String TacticImpressionUsedTable = "ad:impression:used:tactic";
    static final String TacticImpressionUsedBucketTable = "ad:impression:used:tactic:bucket";

    static final String TacticConversionBucketTable = "ad:conversion::tactic:bucket";

    static final String TacticPerformanceImpressionGoalTable = "ad:perf:impressions:goal";
    static final String TacticPerformanceImpressionUnprocessedTable = "ad:perf:impressions:unp";
    static final String TacticPerformanceConversionUnprocessedTable = "ad:perf:conversion:unp";
    static final String TacticPerformanceBudgetUsedUnprocessedTable = "ad:perf:budget:unp";
    static final String TacticPerformanceThresholdStepsTable = "ad:perf:thresh:steps";
    static final String TacticPerformanceScaleStepsTable = "ad:perf:scale:steps";
    static final String TacticPerformanceScaleTable = "ad:perf:scale";
    static final String TacticPerformanceThresholdTable = "ad:perf:threshold";

    static final String PerformanceClockTickStartKey = "ad:perf:clock:start";
    static final String PerformanceClockTickEndKey = "ad:perf:clock:end";
    static final String PricingResetKey = "ad:perf:reset";
}
