package sample.Sistema

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

open class Usuario(open val cpf: String, open val senha: String) {
    override fun toString() = cpf
}

data class Aluno(override val cpf: String, override val senha: String) : Usuario(cpf, senha) {

    override fun toString() = "Aluno/CPF=$cpf/senha=$senha"


}

data class Professor(override val cpf: String, override val senha: String) : Usuario(cpf, senha) {

    override fun toString() = "Professor/CPF=$cpf/senha=$senha"

}

data class Sala(val id: String, var mural: String = "", var listaCPFs: MutableList<String> = mutableListOf()) {

    override fun toString() = "Sala={id=$id/mural=${mural}/listacpfs=$listaCPFs}"

    fun addusuario(u: Usuario?) {
        if (u != null)
            listaCPFs.add(u.cpf)
    }
}

data class Postagem(var mensagem: String = "") {

    lateinit var data: LocalDateTime

    var tipo = "invalido"
        set(value) {
            field = if (value != "atividade" && value != "aviso" && value != "mensagem") {
                "invalido"
            } else
                value
        }

    override fun toString() = "Postagem do tipo $tipo, com mensagem $mensagem"

    fun setDataEntrega(ano: Int, mes: Int, dia: Int, hora: Int = 23, minuto: Int = 59) {
        data = LocalDateTime.parse("$ano-$mes-$dia $hora:$minuto", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
    }

    // Retorna uma string com o tempo ate a entrega, ou retorna todos os valores da string zerados, caso n haja tempo de entrega ou esteja atrasado
    fun TempoParaEntrega(): String {
        fun compararDatas(data1: LocalDateTime, data2: LocalDateTime): String {
            val ano = data1.year - data2.year
            val mes = data1.monthValue - data2.monthValue
            val dia = data1.dayOfMonth - data2.dayOfMonth
            val hora = data1.hour - data2.hour
            val minuto = data1.minute - data2.minute

            return "$dia/$mes/$ano $hora:$minuto"

        }
        return if (::data.isInitialized) {
            if (data.isAfter(LocalDateTime.now())) {
                compararDatas(data, LocalDateTime.now())
            } else {
                "00/00/00 00:00"
            }
        } else {
            "00/00/00 00:00"
        }
    }
}