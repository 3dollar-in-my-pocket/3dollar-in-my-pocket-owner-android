package app.threedollars.manager.feature.storemanagement.model

internal data class TitleModel(
    val title: String,
    val buttonName: String,
    val buttonClick: () -> Unit,
)
