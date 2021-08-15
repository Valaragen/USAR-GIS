import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { MaterialBottomTabScreenProps } from '@react-navigation/material-bottom-tabs';

export type RootStackParamList = {
  Login: undefined;
  Home: undefined;
  Profile: undefined;
  MissionList: { userId: string };
  MissionDetails: {missionId: number};
};

export type LoginScreenProps = NativeStackScreenProps<RootStackParamList, 'Login'>;
export type HomeScreenProps = NativeStackScreenProps<RootStackParamList, 'Home'>;
export type MissionDetailsScreenProps = NativeStackScreenProps<RootStackParamList, 'MissionDetails'>;
export type MissionListScreenProps = MaterialBottomTabScreenProps<RootStackParamList, 'MissionList'>;