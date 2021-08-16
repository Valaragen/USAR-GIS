import color from 'color';
import { DefaultTheme } from "react-native-paper";
import { Theme } from "react-native-paper/lib/typescript/types";
import { primary } from './themeConst';

export const PaperCustomTheme: Theme = {
    dark: false,
    roundness: 4,
    colors: {
        primary: primary,
        accent: '#940F06',
        background: '#FF3C2E',
        surface: '#FFF',
        error: '#B00020',
        text: '#000',
        onSurface: '#000000',
        disabled: 'color(black).alpha(0.26).rgb().string()',
        placeholder: color('#000').alpha(0.54).rgb().string(),
        backdrop: color('#000').alpha(0.5).rgb().string(),
        notification: '#e622c8',
    },
    fonts: DefaultTheme.fonts,
    animation: {
        scale: 1.0,
    },
}

export default PaperCustomTheme;