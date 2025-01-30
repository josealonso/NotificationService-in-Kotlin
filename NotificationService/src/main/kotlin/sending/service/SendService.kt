package com.josealonso.sending.service

interface SendService {
    fun sendMessage(messageData: MessageData, sender: String)
}
