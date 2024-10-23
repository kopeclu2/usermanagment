package cz.moro.usermanagment.controller.response

import io.swagger.v3.oas.annotations.media.Schema

data class PageInfoResponse(
    @Schema(description = "Total elements in database", example = "21", required = true)
    val total: Long,
    @Schema(description = "Current page index, starts at 0", example = "0", required = true)
    val page: Long,
    @Schema(description = "Requested page size", example = "20", required = true)
    val pageSize: Long
)
