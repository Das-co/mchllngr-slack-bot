package script.base.config.block

import com.slack.api.model.block.Blocks.input
import com.slack.api.model.block.LayoutBlock
import com.slack.api.model.block.composition.BlockCompositions.plainText
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.block.element.BlockElements.plainTextInput
import model.script.ScriptId
import com.slack.api.model.block.composition.DispatchActionConfig as SlackDispatchActionConfig

data class ConfigBlockText(
    private val scriptId: ScriptId,
    private val id: ConfigBlockId,
    private val label: PlainTextObject,
    private val hint: PlainTextObject? = null,
    private val placeholder: PlainTextObject? = null,
    private val initialValue: String? = null,
    private val multiline: Boolean = false,
    private val minLength: Int? = null,
    private val maxLength: Int? = null,
    private val dispatchActionConfig: DispatchActionConfig = DispatchActionConfig.ON_ENTER_PRESSED
) : ConfigBlock(scriptId, id) {

    constructor(
        scriptId: ScriptId,
        id: ConfigBlockId,
        label: String,
        hint: String? = null,
        placeholder: String? = null,
        initialValue: String? = null,
        multiline: Boolean = false,
        minLength: Int? = null,
        maxLength: Int? = null,
        dispatchActionConfig: DispatchActionConfig = DispatchActionConfig.ON_ENTER_PRESSED
    ) : this(
        scriptId = scriptId,
        id = id,
        label = plainText(label),
        hint = hint?.let { plainText(it) },
        placeholder = placeholder?.let { plainText(it) },
        initialValue = initialValue,
        multiline = multiline,
        minLength = minLength,
        maxLength = maxLength,
        dispatchActionConfig = dispatchActionConfig
    )

    override fun getLayoutBlock(): LayoutBlock = input { input ->
        input.blockId(blockId)
        input.label(label)
        input.hint(hint)
        input.dispatchAction(true)
        input.element(
            plainTextInput { textInput ->
                textInput.actionId(actionId)
                textInput.placeholder(placeholder)
                textInput.initialValue(initialValue)
                textInput.multiline(multiline)
                textInput.minLength(minLength)
                textInput.maxLength(maxLength)
                textInput.dispatchActionConfig(dispatchActionConfig.config)
            }
        )
    }

    enum class DispatchActionConfig(val config: SlackDispatchActionConfig) {
        ON_ENTER_PRESSED(SlackDispatchActionConfig(listOf("on_enter_pressed"))),
        ON_CHARACTER_ENTERED(SlackDispatchActionConfig(listOf("on_character_entered"))),
        ON_ENTER_PRESSED_OR_CHARACTER_ENTERED(SlackDispatchActionConfig(listOf("on_enter_pressed", "on_character_entered")))
    }
}