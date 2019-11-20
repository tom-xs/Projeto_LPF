{
  mode: 'development',
  resolve: {
    modules: [
      'node_modules'
    ]
  },
  plugins: [],
  module: {
    rules: [
      {
        test: /\.js$/,
        use: [
          'source-map-loader'
        ],
        enforce: 'pre'
      }
    ]
  },
  entry: [
    '/home/tom/intellij ' +
      'ws/Kotlin/Projeto_de_Lpf/ProjetoLPF/build/js/packages/ProjetoLPF/kotlin/ProjetoLPF.js',
    'source-map-support/browser-source-map-support.js'
  ],
  output: {
    path: '/home/tom/intellij ' +
      'ws/Kotlin/Projeto_de_Lpf/ProjetoLPF/build/distributions',
    filename: 'ProjetoLPF-unspecified.js'
  },
  devtool: 'eval-source-map'
}