module.exports = {
  presets: ['module:metro-react-native-babel-preset'],
  plugins: [
    [
      require.resolve('babel-plugin-module-resolver'), 
      {
        root: ["./src"],
        alias: {
          src: "./src",
          api: "./src/api",
          components: "./src/components",
          img: "./src/img",
          store: "./src/store",
          styles: "./src/styles",
          utils: "./src/utils"
        }
      }
    ]
  ]
};
