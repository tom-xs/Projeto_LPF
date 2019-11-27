package sample

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.http.content.*
import io.ktor.response.respondRedirect
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.html.*
import java.io.*

var arquivoContas = File("css_imagens_e_arquivos/Contas.txt")

fun checaExistenciaConta(cpf: String): Boolean {
    arquivoContas.readLines().forEach { x ->
        if (cpf == x) {
            println("Conta de cpf igual ja encontrada")
            return true
        }
    }
    return false
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
                }
                file("ProjetoLPF.js")
            }
            get("/validateLogin") {
                call.respondHtml {
                    var login = call.parameters
                    body {
                        +"checar se o login está no arquivos de cadastros , cpf = ${login["CPF"]} e senha = ${login["Senha"]}"
                    }
                }
            }
            get("/validateCadastro") {
                var parametros = call.parameters

                if (checaExistenciaConta(parametros["CPF"] as String)) {
                    //aviso = "conta ja existente, criar outra"
                    call.respondRedirect("/cadastrar")
                } else {
                    arquivoContas.writeText(
                        "${arquivoContas.readText()}" +
                                "\n---" +
                                "\n${parametros["CPF"]}" +
                                "\n${parametros["senha"]}" +
                                "\n${parametros["tipo"]}"
                    )
                }
                //aviso = "conta criada com sucesso"
                call.respondRedirect("/?aviso=conta+criada")
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
                                            value = "aluno"
                                            +"Aluno"
                                        }
                                        option {
                                            value = "Professor"
                                            +"Professor"
                                        }
                                    }
                                }
                                br {}

                                input(InputType.button) {
                                    id = "cadastrarSubmit"
                                    value = "cadastrar"
                                }
                            }
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
                                //com isso daqui aprendi que os estilos pras tags são nesse esquema aqui de baixo
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
                            if (call.parameters["aviso"] == "conta criada")
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