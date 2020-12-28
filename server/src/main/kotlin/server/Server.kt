package server

import java.io.IOException
import java.net.ServerSocket

class Server(private val port: Int) : Thread() {
    private val handlers: MutableList<ClientHandler> = mutableListOf()

    private val gameBoard = Board()

    val getHandlers
        get(): MutableList<ClientHandler> {
            return handlers
        }

    override fun run() {
        try {
            val serverSocket = ServerSocket(port)
            while (true) {
                println("Accepting client connections..")

                val clientSocket = serverSocket.accept()
                println("Accepting new connection from $clientSocket")

                val clientHandler = ClientHandler(this, clientSocket, gameBoard)
                handlers.add(clientHandler)
                clientHandler.start()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}