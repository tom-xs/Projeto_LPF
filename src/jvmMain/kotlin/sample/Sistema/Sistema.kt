package sample.Sistema

import java.io.File
import java.time.LocalDateTime

open class Usuario(open val cpf: String, open val senha: String) {
    override fun toString() = cpf
}

data class Aluno(override val cpf: String, override val senha: String) : Usuario(cpf, senha) {

    override fun toString() = "Aluno/CPF=$cpf/senha=$senha"


}

data class Professor(override val cpf: String, override val senha: String) : Usuario(cpf, senha) {

    override fun toString() = "Professor/CPF=$cpf/senha=$senha"

}

data class Sala(val id: String, var mural: File, var listaCPFs: MutableList<String> = mutableListOf()) {

    override fun toString() = "Sala={id=$id/mural=$mural/listacpfs=$listaCPFs}"

    fun postar(msg: String, cpf: String) {
        val postagem = Postagem(msg, cpf)

        var novoMural: String

        if (!mural.exists())
            novoMural = "$postagem\n"
        else
            novoMural = "${postagem}" +
                    "──────────────────────────────────────────────────────────────────────────────────────────────────" +
                    "\n${mural.readText()}"

        mural.writeText(novoMural)
    }

    fun addusuario(u: Usuario?) {
        if (u != null)
            listaCPFs.add(u.cpf)
    }
}

data class Postagem(var mensagem: String = "", var cpf: String) {

    fun getData(): String {
        val oi = LocalDateTime.now().toString()
        return oi.replace("T", " às ").dropLast(10)
    }

    override fun toString() = "$mensagem\npostado por: $cpf em ${getData()}\n"

}