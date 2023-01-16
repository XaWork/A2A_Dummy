package com.a2a.app.domain.use_case.user_use_case

import com.a2a.app.domain.use_case.custom_use_case.*

data class BookUseCases(
    val addressListUseCase: AddressListUseCase,
    val categoryUseCase: CategoryUseCase,
    val checkAvailableTimeSlotsUseCase: CheckAvailableTimeSlotsUseCase,
    val estimateBookingUseCase: EstimateBookingUseCase,
    val serviceTypeUseCase: ServiceTypeUseCase,
    val subCategoryUseCase: SubCategoryUseCase,
    val cutoffTimeCheckUseCase: CutoffTimeCheckUseCase
)
