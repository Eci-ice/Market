const webpack = require('webpack');
const path = require('path');

module.exports = {
  transpileDependencies: true,
  filenameHashing: false,//前后端图片文件名保持一致

  // devServer: {
  //   port: 3000,
  // },
  devServer: {
    port: 3000,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        pathRewrite: {
          '^/api': '' // 如果你的后端服务不在根路径下，可以在这里进行配置
        }
      }
    }
  },
  configureWebpack: {
    plugins: [
      new webpack.DefinePlugin({
        __VUE_OPTIONS_API__: "true",
        __VUE_PROD_DEVTOOLS__: "false",
        __VUE_PROD_HYDRATION_MISMATCH_DETAILS__: "false",
      }),
    ],
    resolve: {
      alias: {
        '~@': path.resolve(__dirname, 'src') // 设置 '@' 别名指向项目的 'src' 目录
      }
    }
  },

};


