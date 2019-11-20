package sample

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.http.content.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.html.*
import java.io.*




fun main() {
    embeddedServer(Netty, port = 8080, host = "127.0.0.1") {
        routing {
            get("/validateLogin") {
                call.respondHtml {
                    var login = call.parameters
                    body {
                        +"checar se o login está no arquivos de cadastros , cpf = ${login["CPF"]} e senha = ${login["Senha"]}"
                    }
                }
            }
            get("/validateCadastro"){

            }
            get("/cadastrar") {
                var erro = call.parameters.get("erro")

                call.respondHtml {
                    head {
                        link {
                            rel = "stylesheet"
                            href = "static/estilo/PaginaCadastro.css"
                        }
                        title("POLI Class - LPF!")
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
                                id = "formulario"

                                label {
                                    htmlFor = "CPFInput"
                                    +"digite seu cpf:"
                                    br {}
                                    input(type = InputType.text) {
                                        name = "CPF"
                                        value = ""
                                    }
                                }
                                br{}

                                label {
                                    htmlFor = "SenhaInput"
                                    +"escolha uma senha:"
                                    br {}
                                    input(type = InputType.password) {
                                        id = "SenhaInput"
                                        name = "senha"
                                    }
                                }
                                br{}
                                label {
                                    htmlFor = "SenhaInput2"
                                    +"confirme a senha:"
                                    br {}
                                    input(type = InputType.password) {
                                        id = "SenhaInput"
                                        name = "senha2"
                                    }
                                }


                                br{}

                                input(InputType.submit) {
                                    id = "cadastrarSubmit"
                                    value = "cadastrar"
                                }
                            }
                        }
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
                        title("POLI Class - LPF!")
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
                                    br{}
                                    input(type = InputType.text) {
                                        name = "CPF"
                                        value = "CPF"
                                    }
                                }

                                br{}

                                label {
                                    htmlFor = "SenhaInput"
                                    +"senha:"
                                    br{}
                                    input(type = InputType.password) {
                                        id = "SenhaInput"
                                        name = "senha"
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
                        script(src = "/static/kotlin_to_js/kotlin.js") {}
                        script(src = "/static/ProjetoLPF.js") {}
                    }
                }
            }
            static("/static") {
                staticRootFolder = File("build/js/packages/ProjetoLPF/kotlin/")
                static("kotlin_to_js") {
                    staticRootFolder = File("build/js/packages_imported/kotlin/1.3.50/")
                    file("kotlin.js")
                }
                static("estilo") {
                    staticRootFolder = File("css_e_imagens")
                    file("PaginaInicial.css")
                    file("PaginaCadastro.css")
                }
                file("ProjetoLPF.js")
            }
        }
    }.start(wait = true)
}