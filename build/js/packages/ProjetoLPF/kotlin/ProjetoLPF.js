(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'ProjetoLPF'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'ProjetoLPF'.");
    }
    root.ProjetoLPF = factory(typeof ProjetoLPF === 'undefined' ? {} : ProjetoLPF, kotlin);
  }
}(this, function (_, Kotlin) {
  'use strict';
  var equals = Kotlin.equals;
  var Unit = Kotlin.kotlin.Unit;
  var unboxChar = Kotlin.unboxChar;
  var throwCCE = Kotlin.throwCCE;
  var println = Kotlin.kotlin.io.println_s8jyv4$;
  var iterator = Kotlin.kotlin.text.iterator_gw00vp$;
  var toBoxedChar = Kotlin.toBoxedChar;
  function formatar$lambda(closure$campodetexto, closure$mascara) {
    return function (it) {
      var i = closure$campodetexto.v.value.length;
      var saida = closure$mascara.substring(0, 1);
      var texto = closure$mascara.substring(i);
      if (!equals(texto.substring(0, 1), saida)) {
        closure$campodetexto.v.value = closure$campodetexto.v.value + texto.substring(0, 1);
      }
      return Unit;
    };
  }
  function formatar(mascara, elemento) {
    var campodetexto = {v: elemento};
    campodetexto.v.maxLength = 14;
    campodetexto.v.onkeypress = formatar$lambda(campodetexto, mascara);
  }
  function auxValidateCadastro(cpfInput, senha1, senha2) {
    if (!equals(senha1, senha2)) {
      window.alert('Senhas diferentes');
      return false;
    }
     else if (cpfInput.length < 14) {
      window.alert('CPF muito pequeno');
      return false;
    }
     else if (senha1.length < 6) {
      window.alert('senha deve ter mais que 6 digitos');
      return false;
    }
     else {
      var tmp$;
      tmp$ = iterator(cpfInput);
      while (tmp$.hasNext()) {
        var element = unboxChar(tmp$.next());
        var x = toBoxedChar(element);
        if (unboxChar(x) !== 49 && unboxChar(x) !== 50 && unboxChar(x) !== 51 && unboxChar(x) !== 52 && unboxChar(x) !== 53 && unboxChar(x) !== 54 && unboxChar(x) !== 55 && unboxChar(x) !== 56 && unboxChar(x) !== 57 && unboxChar(x) !== 48 && unboxChar(x) !== 46 && unboxChar(x) !== 45) {
          window.alert('Formato de CPF invalido');
          return false;
        }
      }
    }
    return true;
  }
  function validateCadastro() {
    var tmp$, tmp$_0, tmp$_1, tmp$_2;
    var form = Kotlin.isType(tmp$ = document.getElementById('formulario_cadastro'), HTMLFormElement) ? tmp$ : throwCCE();
    var cpfInput = Kotlin.isType(tmp$_0 = form['CPF'], HTMLInputElement) ? tmp$_0 : throwCCE();
    var senha1 = Kotlin.isType(tmp$_1 = form['senha'], HTMLInputElement) ? tmp$_1 : throwCCE();
    var senha2 = Kotlin.isType(tmp$_2 = form['senha2'], HTMLInputElement) ? tmp$_2 : throwCCE();
    return auxValidateCadastro(cpfInput.value, senha1.value, senha2.value);
  }
  function main$lambda(x) {
    var tmp$;
    if (validateCadastro() === true)
      (Kotlin.isType(tmp$ = document.getElementById('formulario_cadastro'), HTMLFormElement) ? tmp$ : throwCCE()).submit();
    return Unit;
  }
  function main$lambda_0(x) {
    var tmp$;
    if (x.charCode === 13)
      if (validateCadastro() === true)
        (Kotlin.isType(tmp$ = document.getElementById('formulario_cadastro'), HTMLFormElement) ? tmp$ : throwCCE()).submit();
    return Unit;
  }
  function main() {
    var tmp$, tmp$_0, tmp$_1, tmp$_2, tmp$_3, tmp$_4;
    println(document.title);
    if (equals(document.title, 'Tela de Cadastro')) {
      formatar('###.###.###-##', Kotlin.isType(tmp$ = document.getElementById('cpf_input_cadastro'), HTMLInputElement) ? tmp$ : throwCCE());
      if (document.getElementById('erro') != null) {
        window.alert('cpf j\xE1 usado');
        (tmp$_0 = document.location) != null ? (tmp$_0.href = '/cadastrar') : null;
      }
      (Kotlin.isType(tmp$_1 = document.getElementById('cadastrarbtn'), HTMLInputElement) ? tmp$_1 : throwCCE()).onclick = main$lambda;
      document.onkeypress = main$lambda_0;
    }
     else if (equals(document.title, 'POLI Class - LPF')) {
      formatar('###.###.###-##', Kotlin.isType(tmp$_2 = document.getElementById('cpf_input'), HTMLInputElement) ? tmp$_2 : throwCCE());
      if (document.getElementById('mostrar aviso conta criada') != null) {
        window.alert('conta criada com sucesso');
        (tmp$_3 = document.location) != null ? (tmp$_3.href = '/') : null;
      }
       else if (document.getElementById('login nao encontrado') != null) {
        window.alert('conta n\xE3o encontrada');
        (tmp$_4 = document.location) != null ? (tmp$_4.href = '/') : null;
      }
    }
  }
  var package$sample = _.sample || (_.sample = {});
  package$sample.formatar = formatar;
  package$sample.auxValidateCadastro_6hosri$ = auxValidateCadastro;
  package$sample.validateCadastro = validateCadastro;
  package$sample.main = main;
  main();
  Kotlin.defineModule('ProjetoLPF', _);
  return _;
}));

//# sourceMappingURL=ProjetoLPF.js.map
