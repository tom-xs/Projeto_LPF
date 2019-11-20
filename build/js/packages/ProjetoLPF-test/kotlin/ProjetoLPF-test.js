(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin', 'ProjetoLPF', 'kotlin-test'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'), require('ProjetoLPF'), require('kotlin-test'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'ProjetoLPF-test'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'ProjetoLPF-test'.");
    }
    if (typeof ProjetoLPF === 'undefined') {
      throw new Error("Error loading module 'ProjetoLPF-test'. Its dependency 'ProjetoLPF' was not found. Please, check whether 'ProjetoLPF' is loaded prior to 'ProjetoLPF-test'.");
    }
    if (typeof this['kotlin-test'] === 'undefined') {
      throw new Error("Error loading module 'ProjetoLPF-test'. Its dependency 'kotlin-test' was not found. Please, check whether 'kotlin-test' is loaded prior to 'ProjetoLPF-test'.");
    }
    root['ProjetoLPF-test'] = factory(typeof this['ProjetoLPF-test'] === 'undefined' ? {} : this['ProjetoLPF-test'], kotlin, ProjetoLPF, this['kotlin-test']);
  }
}(this, function (_, Kotlin, $module$ProjetoLPF, $module$kotlin_test) {
  'use strict';
  var hello = $module$ProjetoLPF.sample.hello;
  var contains = Kotlin.kotlin.text.contains_li3zpu$;
  var assertTrue = $module$kotlin_test.kotlin.test.assertTrue_ifx8ge$;
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var test = $module$kotlin_test.kotlin.test.test;
  var suite = $module$kotlin_test.kotlin.test.suite;
  function SampleTestsJS() {
  }
  SampleTestsJS.prototype.testHello = function () {
    assertTrue(contains(hello(), 'JS'));
  };
  SampleTestsJS.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'SampleTestsJS',
    interfaces: []
  };
  var package$sample = _.sample || (_.sample = {});
  package$sample.SampleTestsJS = SampleTestsJS;
  suite('sample', false, function () {
    suite('SampleTestsJS', false, function () {
      test('testHello', false, function () {
        return (new SampleTestsJS()).testHello();
      });
    });
  });
  Kotlin.defineModule('ProjetoLPF-test', _);
  return _;
}));

//# sourceMappingURL=ProjetoLPF-test.js.map
