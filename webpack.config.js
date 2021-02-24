var path = require('path');

module.exports = {
    entry: ['@babel/polyfill','./src/main/js/app.js'],
    cache: true,
    devtool: 'source-map',
    mode: 'development',
    output: {
        path: __dirname,
        filename: './src/main/resources/static/built/bundle.js'
    },
    module: {
        rules: [
            {
                test: path.join(__dirname, '.'),
                exclude: /(node_modules)/,
                use: [{
                    loader: 'babel-loader',
                    options: {
                        presets: ["@babel/preset-env", "@babel/preset-react"]
                    }
                }]
            },{
                test: /\.css$/,
                use: ['style-loader', 'css-loader'],
              },
        ]
    }
};