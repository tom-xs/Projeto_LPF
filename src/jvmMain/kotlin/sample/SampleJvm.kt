package sample

import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.http.content.file
import io.ktor.http.content.static
import io.ktor.http.content.staticRootFolder
import io.ktor.response.respondRedirect
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.html.*
import java.io.File
import sample.Sistema.*


var arquivoContas = File("css_imagens_e_arquivos/Contas.txt")

fun getSala(salaid: String?): Sala? {
    if (salaid == null)
        return null
    else {
        var stringCPFs = ""
        arquivoContas.readLines().forEach() { x ->
            if (x.contains("id=$salaid"))
                stringCPFs = x
        }
        var stringMural = stringCPFs.split("/")[1]
        stringMural = stringMural.substringAfter("mural=")
        val stringLista = stringCPFs.split("/")[2]
        val listaCpfs = mutableListOf<String>()
        (stringLista.substring(stringLista.indexOf("[") + 1, stringLista.indexOf("]"))).split(",").forEach { x ->
            listaCpfs.add(x)
        }
        println(stringMural)

        return Sala(salaid, stringMural, listaCpfs)
    }
}

fun getUsuario(cpf: String?): String {
    arquivoContas.readLines().forEach { if (it.contains("/CPF=$cpf/")) return it }
    return ""
}

fun checaExistenciaConta(cpf: String): Boolean {
    arquivoContas.readLines().forEach { x ->
        if (x.contains("/CPF=$cpf/")) {
            return true
        }
    }
    return false
}

fun salaExist(idSala: String?): Boolean {
    arquivoContas.readLines().forEach { if (it.contains("id=$idSala/")) return true }
    return false
}

fun checaCPFSENHA(cpf: String?, senha: String?): Boolean {
    arquivoContas.readLines().forEach { if (it.contains("/CPF=$cpf/senha=$senha/")) return true }
    return false
}

fun addSalaTXT(idSala: String, cpfCriador: String) {
    val sala = Sala(idSala, listaCPFs = mutableListOf(cpfCriador))
    arquivoContas.writeText(
        arquivoContas.readText() + "\n---\n" + "$sala"
    )

}

fun getUsuarioClasse(cpf: String?): Usuario { // TODO lista
    val usuarioString = getUsuario(cpf)
    val cpf2 = usuarioString.split("/")[1].substring(4)
    val senha = usuarioString.split("/")[2].substring(6)
    return if (usuarioString.split("/")[0] == "Aluno")
        Aluno(cpf2, senha)
    else
        Professor(cpf2, senha)
}

fun addCpfToSalaFile(sala: Sala) {
    val novodocumento: String = arquivoContas.readText()
    var stringpramodificar = ""
    arquivoContas.readLines().forEach { x ->
        if (x.contains("Sala={id=${sala.id}/"))
            stringpramodificar = x
    }
    var listaString = "["
    sala.listaCPFs.forEach { x ->
        if (sala.listaCPFs[sala.listaCPFs.size - 1] != x)
            listaString += "$x,"
        else
            listaString += "$x"
    }

    val novaString = "Sala={id=${sala.id}/mural=${sala.mural}/listacpfs=$listaString]}"

    var retorno = novodocumento.replace(stringpramodificar, novaString)

    arquivoContas.writeText(retorno)
}

fun main() {
    embeddedServer(Netty, port = 8080, host = "127.0.0.1") {
        routing {
            static("/static") {
                staticRootFolder = File("build/js/packages/ProjetoLPF/kotlin/")
                static("kotlin_to_js") {
                    staticRootFolder = File("build/js/packages_imported/kotlin/1.3.50/")
                    file("kotlin.js")
                }
                static("estilo") {
                    staticRootFolder = File("css_imagens_e_arquivos")
                    file("PaginaInicial.css")
                    file("PaginaCadastro.css")
                    file("sala_de_aula.css")
                }
                file("ProjetoLPF.js")
            }
            get("/validateLogin") {
                val login = call.parameters
                if (checaCPFSENHA(login["CPF"], login["senha"])) {
                    call.respondRedirect("/home?CPF=${login["CPF"]}")
                } else {
                    call.respondRedirect("/?aviso=login+nao+encontrado")
                }
            }
            get("/validateCadastro") {
                val parametros = call.parameters

                if (checaExistenciaConta(parametros["CPF"] as String)) {
                    //aviso = "conta ja existente, criar outra"
                    call.respondRedirect("/cadastrar?aviso=cpf+ja+usado")
                } else {
                    arquivoContas.writeText(
                        "${parametros["tipo"]}/CPF=${parametros["CPF"]}/senha=${parametros["senha"]}/"
                                + "\n---\n" + arquivoContas.readText()
                    )
                    //aviso = "conta criada com sucesso"
                    call.respondRedirect("/?aviso=conta+criada")
                }
            }
            get("/validateSala") {
                //todo colocar mensagens
                val parametros = call.parameters
                val usuario = getUsuarioClasse(parametros["CPF"])
                val idSala = parametros["idSala"]
                if (!salaExist(idSala))
                    if (usuario is Professor) {
                        //todo mensagem sala nova criada
                        addSalaTXT(idSala as String, usuario.cpf)
                        call.respondRedirect(
                            "/sala_de_aula?CPF=${parametros["cpf"]}?idSala=${idSala.replace(" ", "+")}"
                        )
                    } else {
                        //todo mensagem de sala não existente
                    }
                else {
                    var sala = getSala(idSala)
                    if (sala?.listaCPFs!!.contains(usuario.cpf)) {
                        call.respondRedirect("/sala_de_aula?CPF=${parametros["cpf"]}?idSala=$idSala")
                    } else {
                        if (!sala.listaCPFs.contains(usuario.cpf)) {
                            sala.addusuario(usuario)
                            addCpfToSalaFile(sala)
                        }
                        call.respondRedirect("/sala_de_aula?CPF=${parametros["cpf"]}?idSala=$idSala")
                    }
                }
            }
            get("sala_de_aula") {
                call.respondHtml {
                    head {
                        link {
                            rel = "stylesheet"
                            href = "static/estilo/sala_de_aula.css"
                        }
                        meta {
                            charset = "UTF-8"
                        }
                        title("Sala De Aula")
                    }
                    body {
                        div {
                            id = "layout"
                            header {
                                id = "header"
                                h1 {
                                    +"Sala De Aula"
                                }
                            }
                            section {
                                id = "mural"
                                form {
                                    id = "form"
                                    action = "sala_de_aula"
                                    method = FormMethod.get
                                    fieldSet {
                                        id = "message"
                                        legend {
                                            +"Mensagem: "
                                        }
                                        input(InputType.text) {
                                            id = "mensagem"
                                            name = "message"
                                            maxLength = "500"
                                            size = "130"
                                        }
                                        input(InputType.submit) {
                                            value = "Postar"
                                            id = "bottom_submit"
                                        }
                                    }
                                }
                            }
                        }
                        aside {
                            id = "students"
                        }
                    }
                }
            }
            get("/home") {
                val login = call.parameters
                val usuario = getUsuarioClasse(login["CPF"])

                call.respondHtml {
                    head {
                        title("Home")
                    }
                    body {
                        div {
                            // Cabeçalho
                            style = "text-align:center"
                            a {
                                +"Olá ${if (usuario is Aluno) "Aluno" else "Professor"}"
                            }
                        }
                        div {
                            style = "text-align:center"
                            br {}
                            +"Escreva o nome da sala para entrar: "
                            form {
                                action = "/validateSala?"
                                label {
                                    htmlFor = "IDSalaInput"
                                    br {}
                                    input(type = InputType.text) {
                                        id = "idSala_input"
                                        name = "idSala"
                                        placeholder = "Nome da Sala"
                                    }
                                    input(InputType.submit) {
                                        id = "idSala_button"
                                        value = "Ir à Sala"
                                    }
                                    input(InputType.hidden) {
                                        name = "CPF"
                                        value = usuario.cpf
                                    }
                                }
                            }
                        }
                    }
                }
            }
            get("/cadastrar") {
                call.respondHtml {
                    head {
                        link {
                            rel = "stylesheet"
                            href = "static/estilo/PaginaCadastro.css"
                        }
                        title("Tela de Cadastro")
                    }
                    body {
                        div {
                            classes = setOf("cabeçalho")
                            h1 {
                                a {
                                    href = "/"
                                    +"POLI CLASS"
                                }
                            }
                        }
                        div {
                            classes = setOf("formulario")
                            //espaço dedicado para login e cadastro
                            form {
                                //formulario para login
                                action = "/validateCadastro"
                                id = "formulario_cadastro"

                                label {
                                    htmlFor = "CPFInput"
                                    +"digite seu cpf:"
                                    br {}
                                    input(type = InputType.text) {
                                        id = "cpf_input_cadastro"
                                        name = "CPF"
                                        value = ""
                                        autoComplete = false
                                    }
                                }
                                br {}

                                label {
                                    htmlFor = "SenhaInput"
                                    +"escolha uma senha:"
                                    br {}
                                    input(type = InputType.password) {
                                        id = "SenhaInput"
                                        name = "senha"
                                    }
                                }
                                br {}
                                label {
                                    htmlFor = "SenhaInput2"
                                    +"confirme a senha:"
                                    br {}
                                    input(type = InputType.password) {
                                        id = "SenhaInput2"
                                        name = "senha2"
                                    }
                                }


                                br {}
                                label {
                                    htmlFor = "tipoInput"
                                    +"tipo de conta"
                                    br {

                                    }
                                    select {
                                        name = "tipo"
                                        option {
                                            value = "Aluno"
                                            +"Aluno"
                                        }
                                        option {
                                            value = "Professor"
                                            +"Professor"
                                        }
                                    }
                                    br {}
                                    input(InputType.button) {
                                        id = "cadastrarbtn"
                                        value = "cadastrar"
                                    }
                                }
                                br {}

                            }
                        }
                        div {
                            if (call.parameters["aviso"] == "cpf ja usado")
                                id = "erro"
                            else
                                id = "nao mostrar aviso"
                        }
                        script(src = "/static/kotlin_to_js/kotlin.js") {}
                        script(src = "/static/ProjetoLPF.js") {}
                    }
                }
            }
            get("/") {
                call.respondHtml {
                    head {
                        //carregar o estilo css da página
                        link {
                            rel = "stylesheet"
                            href = "static/estilo/PaginaInicial.css"
                        }
                        title("POLI Class - LPF")
                    }
                    body {
                        div {
                            //cabeçalho
                            classes = setOf("cabeçalho")
                            h1 {
                                a {
                                    href = "/"
                                    +"POLI CLASS"
                                }
                            }
                        }
                        div {
                            classes = setOf("formulario")
                            //espaço dedicado para login e cadastro
                            form {
                                //formulario para login
                                action = "/validateLogin"
                                id = "formulario"

                                label {
                                    htmlFor = "CPFInput"
                                    +"cpf:"
                                    br {}
                                    input(type = InputType.text) {
                                        id = "cpf_input"
                                        name = "CPF"
                                        placeholder = "cpf"
                                    }
                                }

                                br {}

                                label {
                                    htmlFor = "SenhaInput"
                                    +"senha:"
                                    br {}
                                    input(type = InputType.password) {
                                        id = "SenhaInput"
                                        name = "senha"
                                        placeholder = "senha"
                                    }
                                }


                                p {}

                                input(InputType.submit) {
                                    id = "loginsubmit"
                                    value = "entrar"
                                }
                                a {
                                    href = "/cadastrar"
                                    input(InputType.button) {
                                        id = "cadastrarbtn"
                                        value = "cadastrar"
                                    }
                                }
                            }
                        }
                        div {
                            if (call.parameters["aviso"] == "cpf ja usado")
                                id = "erro"
                            else if (call.parameters["aviso"] == "login nao encontrado")
                                id = "login nao encontrado"
                            else if (call.parameters["aviso"] == "conta criada")
                                id = "mostrar aviso conta criada"
                            else
                                id = "nao mostrar aviso"
                        }
                        script(src = "/static/kotlin_to_js/kotlin.js") {}
                        script(src = "/static/ProjetoLPF.js") {}
                    }
                }
            }
        }
    }.start(wait = true)
}