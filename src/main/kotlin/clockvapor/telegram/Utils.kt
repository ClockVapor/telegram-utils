package clockvapor.telegram

import me.ivmg.telegram.Bot
import me.ivmg.telegram.entities.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val whitespaceRegex = Regex("\\s+")

fun log(s: String) {
    val timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    println("$timestamp: $s")
}

fun log(t: Throwable) {
    log(t.localizedMessage)
    t.printStackTrace()
}

inline fun tryOrLog(f: () -> Unit) = try {
    f()
} catch (e: Exception) {
    log(e)
}

inline fun <T> tryOrNull(reportException: Boolean = true, f: () -> T): T? = try {
    f()
} catch (e: Exception) {
    if (reportException) log(e)
    null
}

inline fun <T> tryOrDefault(default: T, reportException: Boolean = true, f: () -> T): T = try {
    f()
} catch (e: Exception) {
    if (reportException) log(e)
    default
}

inline fun <T> trySuccessful(reportException: Boolean = true, f: () -> T): Boolean = try {
    f()
    true
} catch (e: Exception) {
    if (reportException) log(e)
    false
}

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
