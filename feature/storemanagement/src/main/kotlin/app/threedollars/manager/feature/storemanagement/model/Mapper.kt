package app.threedollars.manager.feature.storemanagement.model

import app.threedollars.common.ext.toDoubleDefault
import app.threedollars.common.ext.toIntDefault
import app.threedollars.common.ext.toStringDefault
import app.threedollars.domain.dto.AccountNumbersDto
import app.threedollars.domain.dto.AppearanceDaysDto
import app.threedollars.domain.dto.BankDto
import app.threedollars.domain.dto.BossEnumsDto
import app.threedollars.domain.dto.BossStoreRetrieveDto
import app.threedollars.domain.dto.CategoriesDto
import app.threedollars.domain.dto.EnumsDto
import app.threedollars.domain.dto.FeedbackFullDto
import app.threedollars.domain.dto.FeedbackTypesDto
import app.threedollars.domain.dto.LocationDto
import app.threedollars.domain.dto.MenusDto
import app.threedollars.domain.dto.OpenStatusDto
import app.threedollars.domain.dto.OpeningHoursDto
import app.threedollars.domain.dto.StoreCategoriesDto
import app.threedollars.manager.feature.storemanagement.components.emptyBusinessSchedules
import app.threedollars.manager.feature.storemanagement.convertImageUrlToRequestBody

internal fun AppearanceDaysDto.dtoToVo() = AppearanceDaysVo(
    dayOfTheWeek = dayOfTheWeek.toStringDefault(),
    openingHours = openingHours.dtoToVo(),
    locationDescription = locationDescription.toStringDefault(),
)

internal fun BossStoreRetrieveDto?.dtoToVo() = BossStoreRetrieveVo(
    appearanceDays = this?.appearanceDays.orEmpty().map { it.dtoToVo() },
    bossStoreId = this?.bossStoreId.toStringDefault(),
    categories = this?.categories.orEmpty().map { it.dtoToVo() },
    createdAt = this?.createdAt.toStringDefault(),
    distance = this?.distance.toIntDefault(),
    imageUrl = this?.imageUrl.toStringDefault(),
    introduction = this?.introduction.toStringDefault(),
    location = this?.location.dtoToVo(),
    menus = this?.menus.orEmpty().map { it.dtoToVo() },
    name = this?.name.toStringDefault(),
    openStatus = this?.openStatus.dtoToVo(),
    snsUrl = this?.snsUrl.toStringDefault(),
    updatedAt = this?.updatedAt.toStringDefault(),
    accountNumbers = this?.accountNumbersDto?.map { it.dtoToVo() } ?: listOf()
)

internal fun CategoriesDto.dtoToVo() = CategoriesVo(
    category = category.toStringDefault(),
    categoryId = categoryId.toStringDefault(),
    description = description.toStringDefault(),
    imageUrl = imageUrl.toStringDefault(),
    isNew = isNew ?: false,
    name = name.toStringDefault(),
)

internal fun LocationDto?.dtoToVo() = LocationVo(
    latitude = this?.latitude.toDoubleDefault(),
    longitude = this?.longitude.toDoubleDefault(),
)

internal fun MenusDto.dtoToVo() = MenusVo(
    imageUrl = imageUrl,
    name = name,
    price = price,
)

internal fun OpeningHoursDto?.dtoToVo() = OpeningHoursVo(
    startTime = this?.startTime.toStringDefault(),
    endTime = this?.endTime.toStringDefault(),
)

internal fun OpenStatusDto?.dtoToVo() = OpenStatusVo(
    openStartDateTime = this?.openStartDateTime?.toStringDefault(),
    status = this?.status?.toStringDefault(),
)

internal fun AccountNumbersDto.dtoToVo() = AccountNumbersVo(
    bankVo = bankDto.dtoToVo(),
    accountHolder = accountHolder,
    accountNumber = accountNumber,
    description = description,
)

internal fun BankDto.dtoToVo() = BankVo(
    key = key,
    description = description,
)

internal fun StoreCategoriesDto.dtoToVo() = StoreCategoriesVo(
    category = category,
    categoryId = categoryId,
    description = description,
    imageUrl = imageUrl,
    isNew = isNew,
    name = name,
)

internal fun AppearanceDaysVo.toBusinessSchedule(): BusinessScheduleModel {
    val openingHours = openingHours.let {
        "${it.startTime.toStringDefault()} - ${it.endTime.toStringDefault()}"
    }
    val dayOfTheWeek = dayOfTheWeek.toStringDefault()
    val dayOfTWeek =
        emptyBusinessSchedules.find { it.dayOfTheWeek == dayOfTheWeek }?.dayOfTWeek.toStringDefault()
    val isWeekend = dayOfTWeek == "일요일" || dayOfTWeek == "토요일"
    return BusinessScheduleModel(dayOfTWeek, dayOfTheWeek, locationDescription.toStringDefault(), openingHours, openingHours != "휴무", isWeekend)
}

internal fun MenuModel.toDto(): MenusDto =
    MenusDto(
        imageUrl = this.imageUrl,
        name = this.name,
        price = this.price
    )

internal fun MenusVo.toModel(): MenuModel {
    val requestBody = convertImageUrlToRequestBody(imageUrl.toString())
    return MenuModel(
        imageUrl = imageUrl,
        name = name,
        price = price,
        imageRequestBody = requestBody,
    )
}

internal fun BossEnumsDto.dtoToVo() = bankType.map { it.dtoToVo() }

internal fun EnumsDto.dtoToVo() = BankTypeVo(
    key = key,
    description = description,
)

internal fun FeedbackFullDto.dtoToVo() = FeedbackFullVo(
    count = count,
    feedbackType = feedbackType,
    ratio = ratio,
)
internal fun FeedbackTypesDto.dtoToVo() = FeedbackTypesVo(
    description = description,
    emoji = emoji,
    feedbackType = feedbackType,
)