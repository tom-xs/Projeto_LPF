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
    'C:\\Users\\oipxt\\Desktop\\LPF ' +
      'teste\\build\\js\\packages\\ProjetoLPF\\kotlin\\ProjetoLPF.js',
    'source-map-support/browser-source-map-support.js'
  ],
  output: {
    path: 'C:\\Users\\oipxt\\Desktop\\LPF teste\\build\\distributions',
    filename: 'ProjetoLPF-unspecified.js'
  },
  devtool: 'eval-source-map'
}