package clockvapor.telegram

import me.ivmg.telegram.Bot
import me.ivmg.telegram.entities.*

fun getMessageEntityText(message: Message, entity: MessageEntity): String =
    message.text!!.substring(entity.offset, entity.offset + entity.length)

fun MessageEntity.isMention(): Boolean =
    type == "mention" || type == "text_mention"

fun createInlineMention(text: String, userId: String): String =
    "[$text](tg://user?id=$userId)"

fun isAdmin(bot: Bot, chat: Chat, userId: Long): Boolean = if (chat.allMembersAreAdministrators == true) {
    true
} else {
    getChatMember(bot, chat.id, userId)?.let {
        it.status == "creator" || it.status == "administrator"
    } ?: false
}

fun getChatMember(bot: Bot, chatId: Long, userId: Long): ChatMember? =
    bot.getChatMember(chatId, userId).first?.takeIf { it.isSuccessful }?.body()?.takeIf { it.ok }?.result

fun matchesCommand(text: String, command: String, botUsername: String): Boolean =
    text == "/$command" || text == "/$command@$botUsername"

val User.displayName: String
    get() = lastName?.takeIf { it.isNotBlank() }?.let { "$firstName $it" } ?: firstName
