const webpack = require('webpack');

module.exports = {
  transpileDependencies: true,
  filenameHashing: false,//前后端图片文件名保持一致

  devServer: {
    port: 3000,
  },
  configureWebpack: {
    plugins: [
      new webpack.DefinePlugin({
        __VUE_OPTIONS_API__: "true",
        __VUE_PROD_DEVTOOLS__: "false",
        __VUE_PROD_HYDRATION_MISMATCH_DETAILS__: "false",
      }),
    ],
  },
};


