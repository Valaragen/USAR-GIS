import { NativeStackScreenProps } from '@react-navigation/native-stack';

export type RootStackParamList = {
  Login: undefined;
  MissionList: { userId: string };
  MissionDetails: {missionId: number};
};

export type LoginScreenProps = NativeStackScreenProps<RootStackParamList, 'Login'>;
export type MissionListScreenProps = NativeStackScreenProps<RootStackParamList, 'MissionList'>;
export type MissionDetailsScreenProps = NativeStackScreenProps<RootStackParamList, 'MissionDetails'>;