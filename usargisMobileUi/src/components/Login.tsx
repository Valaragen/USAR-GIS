import React from 'react';
import { View, Text, Pressable, Image, StyleSheet } from 'react-native';
import { useKeycloak } from '@react-keycloak/native';

function Login() {
  const { keycloak } = useKeycloak();

  return (
    <View style={style.container}>
      <Image
        style={style.image}
        source={require('../img/logo-usar-gis.png')}
      />
      <View>
        <Pressable style={({ pressed }) => [
          style.button,
          pressed && style.buttonPressed
        ]} onPress={() => keycloak?.login()}>
          <Text style={style.buttonText}>Connexion</Text>
        </Pressable>
        <Pressable style={({ pressed }) => [
          style.button,
          pressed && style.buttonPressed,
          style.marginTop
        ]} onPress={() => keycloak?.register()}>
          <Text style={style.buttonText}>Inscription</Text>
        </Pressable>
      </View>
    </View>
  );
};

const style = StyleSheet.create({
  container: {
    flex: 1,
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'space-evenly'
  },
  image: {
    resizeMode: 'contain',
    height: '30%'
  },
  text: {
    fontSize: 22,
    textAlign: 'center'
  },
  button: {
    backgroundColor: '#e22013',
    color: '#fff',
    padding: 10,
    borderRadius: 6
  },
  buttonPressed: {
    backgroundColor: '#ff3d2f'
  },
  buttonText: {
    fontSize: 22,
    color: 'white'
  },
  marginTop: {
    marginTop: 20
  }
});

export default Login;