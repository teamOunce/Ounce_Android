package com.teamounce.ounce.feed.ui

enum class Preference(
    private val preference: Int,
    private val comment: String
) {
    ONE(1, "이거 아니야, 팽~"),
    TWO(2, "깨작깨작..."),
    THREE(3, "먹을만하지만 뭔가 부족해!"),
    FOUR(4, "와구와구 맛있어"),
    FIVE(5, "찾았다 취향 저격 ♡");

    private fun hasPreference(preference: Int): Boolean = this.preference == preference

    companion object {
        fun setCommentByPreference(preference: Int): String {
            return values().find { it.hasPreference(preference) }?.comment
                ?: throw IllegalArgumentException("선호도 설정이 잘못되었습니다. 선호도: ${preference}")
        }
    }
}