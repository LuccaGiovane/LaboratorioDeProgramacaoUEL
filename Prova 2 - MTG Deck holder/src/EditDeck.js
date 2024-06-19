import React, { useState } from 'react';
import { View, Text, TextInput, TouchableOpacity, StyleSheet } from 'react-native';

const EditDeck = ({ route, navigation }) => {
  const { deck, editDeck } = route.params;
  const [name, setName] = useState(deck.deck);
  const [format, setFormat] = useState(deck.format);

  const handleSave = () => {
    editDeck(deck.id, name, format);
    navigation.goBack();
  };

  return (
    <View style={styles.container}>
      <Text style={styles.label}>Deck Name</Text>
      <TextInput
        style={styles.input}
        value={name}
        onChangeText={setName}
      />
      <Text style={styles.label}>Format</Text>
      <TextInput
        style={styles.input}
        value={format}
        onChangeText={setFormat}
      />
      <TouchableOpacity style={styles.button} onPress={handleSave}>
        <Text style={styles.buttonText}>Save</Text>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#2E2E2E',
    padding: 16,
  },
  label: {
    color: '#fff',
    fontWeight: 'bold',
    marginBottom: 8,
  },
  input: {
    backgroundColor: '#474A51',
    color: '#fff',
    borderRadius: 10,
    padding: 10,
    marginBottom: 16,
  },
  button: {
    backgroundColor: 'purple',
    padding: 10,
    borderRadius: 5,
    alignItems: 'center',
  },
  buttonText: {
    color: '#000',
    fontWeight: 'bold',
  },
});

export default EditDeck;
