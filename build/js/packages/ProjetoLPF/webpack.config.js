var config = {
  mode: 'development',
  resolve: {
    modules: [
      "node_modules"
    ]
  },
  plugins: [],
  module: {
    rules: []
  }
};

// entry
if (!config.entry) config.entry = [];
config.entry.push("/home/tom/intellij ws/Kotlin/Projeto_de_Lpf/ProjetoLPF/build/js/packages/ProjetoLPF/kotlin/ProjetoLPF.js");
config.output = {
    path: "/home/tom/intellij ws/Kotlin/Projeto_de_Lpf/ProjetoLPF/build/distributions",
    filename: "ProjetoLPF-unspecified.js"
};

// source maps
config.module.rules.push({
        test: /\.js$/,
        use: ["source-map-loader"],
        enforce: "pre"
});
config.devtool = 'eval-source-map';

// source maps runtime
if (!config.entry) config.entry = [];
config.entry.push('source-map-support/browser-source-map-support.js');

// save evaluated config file
var util = require('util');
var fs = require("fs");
var evaluatedConfig = util.inspect(config, {showHidden: false, depth: null, compact: false});
fs.writeFile("/home/tom/intellij ws/Kotlin/Projeto_de_Lpf/ProjetoLPF/build/reports/webpack/ProjetoLPF/webpack.config.evaluated.js", evaluatedConfig, function (err) {});

// Report progress to console
(function(config) {
    const webpack = require('webpack');
    const handler = (percentage, message, ...args) => {
        const p = percentage*100;
        let msg = Math.trunc(p/100) + Math.trunc(p%100) + '% ' + message + ' ' + args.join(' ');
        msg = msg.replace(new RegExp("/home/tom/intellij ws/Kotlin/Projeto_de_Lpf/ProjetoLPF/build/js", 'g'), '');
        console.log(msg);
    };

    config.plugins.push(new webpack.ProgressPlugin(handler))
})(config);
module.exports = config
