package app.threedollars.manager.util

import app.threedollars.common.ext.toBooleanDefault
import app.threedollars.common.ext.toDoubleDefault
import app.threedollars.common.ext.toIntDefault
import app.threedollars.common.ext.toStringDefault
import app.threedollars.domain.dto.*
import app.threedollars.manager.vo.*

fun AppearanceDaysDto.dtoToVo() = AppearanceDaysVo(
    dayOfTheWeek = dayOfTheWeek.toStringDefault(),
    openingHours = openingHours.dtoToVo(),
    locationDescription = locationDescription.toStringDefault(),
)

fun BossEnumsDto.dtoToVo() = BossEnumsVo(
    paymentMethodType = paymentMethodType.map { it.dtoToVo() },
    bossStoreOpenType = bossStoreOpenType.map { it.dtoToVo() },
    feedbackTargetType = feedbackTargetType.map { it.dtoToVo() },
    advertisementPositionType = advertisementPositionType.map { it.dtoToVo() },
    userMenuCategoryType = userMenuCategoryType.map { it.dtoToVo() },
    pushPlatformType = pushPlatformType.map { it.dtoToVo() },
    bossRegistrationStatus = bossRegistrationStatus.map { it.dtoToVo() },
    storeType = storeType.map { it.dtoToVo() },
    applicationType = applicationType.map { it.dtoToVo() },
    dayOfTheWeek = dayOfTheWeek.map { it.dtoToVo() },
    pushOptionsType = pushOptionsType.map { it.dtoToVo() },
    fileType = fileType.map { it.dtoToVo() },
    bossAccountSocialType = bossAccountSocialType.map { it.dtoToVo() },
    boosRegistrationRejectReasonType = boosRegistrationRejectReasonType.map { it.dtoToVo() },
    deleteReasonType = deleteReasonType.map { it.dtoToVo() },
    visitType = visitType.map { it.dtoToVo() },
    faqCategory = faqCategory.map { it.dtoToVo() },
    storeSalesType = storeSalesType.map { it.dtoToVo() },
    userSocialType = userSocialType.map { it.dtoToVo() },
    feedbackEmojiType = feedbackEmojiType.map { it.dtoToVo() },
    bankType = bankType.map { it.dtoToVo() },
)

fun BossStoreRetrieveAroundDto.dtoToVo() = BossStoreRetrieveAroundVo(
    bossStoreId = bossStoreId.toStringDefault(),
    categories = categories.map { it.dtoToVo() },
    createdAt = createdAt.toStringDefault(),
    distance = distance.toIntDefault(),
    location = location?.dtoToVo() ?: LocationVo(),
    menus = menus.map { it.dtoToVo() },
    name = name.toStringDefault(),
    openStatus = openStatus.dtoToVo(),
    totalFeedbacksCounts = totalFeedbacksCounts.toIntDefault(),
    updatedAt = updatedAt.toStringDefault(),
)

fun BossStoreRetrieveDto?.dtoToVo() = BossStoreRetrieveVo(
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

fun CategoriesDto.dtoToVo() = CategoriesVo(
    category = category.toStringDefault(),
    categoryId = categoryId.toStringDefault(),
    description = description.toStringDefault(),
    imageUrl = imageUrl.toStringDefault(),
    isNew = isNew ?: false,
    name = name.toStringDefault(),
)

fun CategoryInfoDto.dtoToVo() = CategoryInfoVo(
    category = category,
    description = description,
    displayOrder = displayOrder,
)

fun ContentsDto.dtoToVo() = ContentsVo(
    date = date,
    feedbacks = feedbacks.map { it.dtoToVo() },
)

fun CursorDto.dtoToVo() = CursorVo(
    hasMore = hasMore,
    nextCursor = nextCursor,
)

fun EnumsDto.dtoToVo() = EnumsVo(
    key = key,
    description = description,
)

fun FaqCategoriesDto.dtoToVo() = FaqCategoriesVo(
    category = category,
    description = description,
    displayOrder = displayOrder,
)

fun FaqDto.dtoToVo() = FaqVo(
    answer = answer,
    categoryInfo = categoryInfo.dtoToVo(),
    question = question,
)

fun FavoriteDto?.dtoToVo() = FavoriteVo(
    isFavorite = this?.isFavorite?.toBooleanDefault() ?: false,
)

fun FeedbackFullDto.dtoToVo() = FeedbackFullVo(
    count = count,
    feedbackType = feedbackType,
    ratio = ratio,
)

fun FeedbackSpecificDto.dtoToVo() = FeedbackSpecificVo(
    contents = contents.map { it.dtoToVo() },
    cursor = cursor.dtoToVo(),
)

fun FeedbacksDto.dtoToVo() = FeedbacksVo(
    count = count,
    feedbackType = feedbackType,
)

fun FeedbackTypesDto.dtoToVo() = FeedbackTypesVo(
    description = description,
    emoji = emoji,
    feedbackType = feedbackType,
)

fun ImageUploadDto.dtoToVo() = ImageUploadVo(
    imageUrl = imageUrl,
)

fun LocationDto?.dtoToVo() = LocationVo(
    latitude = this?.latitude.toDoubleDefault(),
    longitude = this?.longitude.toDoubleDefault(),
)

fun LoginDto.dtoToVo() = LoginVo(
    bossId = bossId,
    token = token,
)

fun MenusDto.dtoToVo() = MenusVo(
    imageUrl = imageUrl,
    name = name,
    price = price,
)

fun OpeningHoursDto?.dtoToVo() = OpeningHoursVo(
    startTime = this?.startTime.toStringDefault(),
    endTime = this?.endTime.toStringDefault(),
)

fun OpenStatusDto?.dtoToVo() = OpenStatusVo(
    openStartDateTime = this?.openStartDateTime?.toStringDefault(),
    status = this?.status?.toStringDefault(),
)

fun StoreCategoriesDto.dtoToVo() = StoreCategoriesVo(
    category = category,
    categoryId = categoryId,
    description = description,
    imageUrl = imageUrl,
    isNew = isNew,
    name = name,
)

fun AccountNumbersDto.dtoToVo() = AccountNumbersVo(
    bankVo = bankDto.dtoToVo(),
    accountHolder = accountHolder,
    accountNumber = accountNumber,
    description = description,
)

fun BankDto.dtoToVo() = BankVo(
    key = key,
    description = description,
)
