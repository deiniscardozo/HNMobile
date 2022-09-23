data class ProcessingTimingsMS(
    val afterFetch: AfterFetch,
    val fetch: Fetch,
    val getIdx: GetIdx,
    val total: Int
)