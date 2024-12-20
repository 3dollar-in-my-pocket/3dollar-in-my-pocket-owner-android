package app.threedollars.manager.feature.home.model

import app.threedollars.common.ext.toDoubleDefault
import app.threedollars.common.ext.toStringDefault
import app.threedollars.domain.dto.AccountNumbersDto
import app.threedollars.domain.dto.AppearanceDaysDto
import app.threedollars.domain.dto.BankDto
import app.threedollars.domain.dto.BossStoreRetrieveAroundDto
import app.threedollars.domain.dto.BossStoreRetrieveDto
import app.threedollars.domain.dto.CategoriesDto
import app.threedollars.domain.dto.LocationDto
import app.threedollars.domain.dto.MenusDto
import app.threedollars.domain.dto.OpenStatusDto
import app.threedollars.domain.dto.OpeningHoursDto
import app.threedollars.manager.feature.home.StoreStateType

internal fun BossStoreRetrieveAroundDto.dtoToVo() = BossStoreRetrieveAroundVo(
    location = location?.dtoToVo() ?: LocationVo(),
)

internal fun BossStoreRetrieveDto?.dtoToVo() = BossStoreRetrieveVo(
    bossStoreId = this?.bossStoreId.toStringDefault(),
    openStatus = this?.openStatus.dtoToVo(),
)

internal fun LocationDto?.dtoToVo() = LocationVo(
    latitude = this?.latitude.toDoubleDefault(),
    longitude = this?.longitude.toDoubleDefault(),
)

internal fun OpenStatusDto?.dtoToVo() = OpenStatusVo(
    openStartDateTime = this?.openStartDateTime?.toStringDefault(),
    status = if (this?.status?.toStringDefault() == "OPEN") StoreStateType.OPEN else StoreStateType.CLOSE,
)