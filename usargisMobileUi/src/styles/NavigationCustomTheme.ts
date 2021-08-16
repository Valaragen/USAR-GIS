import { Theme } from "@react-navigation/native";
import { primary } from "./themeConst";

const NavigationCustomTheme: Theme = {
    dark: false,
    colors: {
      primary: primary,
      background: 'rgb(242, 242, 242)',
      card: 'rgb(255, 255, 255)',
      text: 'rgb(28, 28, 30)',
      border: 'rgb(199, 199, 204)',
      notification: 'rgb(255, 69, 58)',
    },
  };

  export default NavigationCustomTheme;