package app.threedollars.manager.util

import app.threedollars.common.ext.toBooleanDefault
import app.threedollars.domain.dto.*
import app.threedollars.manager.feature.storemanagement.model.StoreCategoriesVo
import app.threedollars.manager.vo.*

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

fun FeedbackSpecificDto.dtoToVo() = FeedbackSpecificVo(
    contents = contents.map { it.dtoToVo() },
    cursor = cursor.dtoToVo(),
)

fun FeedbacksDto.dtoToVo() = FeedbacksVo(
    count = count,
    feedbackType = feedbackType,
)

fun ImageUploadDto.dtoToVo() = ImageUploadVo(
    imageUrl = imageUrl,
)

fun LoginDto.dtoToVo() = LoginVo(
    bossId = bossId,
    token = token,
)

fun StoreCategoriesDto.dtoToVo() = StoreCategoriesVo(
    category = category,
    categoryId = categoryId,
    description = description,
    imageUrl = imageUrl,
    isNew = isNew,
    name = name,
)
