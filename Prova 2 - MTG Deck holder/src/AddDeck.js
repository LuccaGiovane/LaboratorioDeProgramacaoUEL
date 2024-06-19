import React, { useState } from 'react';
import { View, TextInput, StyleSheet, TouchableOpacity, Text } from 'react-native';
import uuid from './uuid'; // tive que fazer uma uuid com uma funçao reba pq tava dando pau

const AddDeck = ({ addDeck }) => {
  const [deckName, setDeckName] = useState('');
  const [format, setFormat] = useState('');

  const handleAddDeck = () => {
    if (deckName && format) {
      addDeck({ deck: deckName, format, id: uuid(), cards: [] });
      setDeckName('');
      setFormat('');
    }
  };

  return (
    <View style={styles.container}>
      <TextInput
        style={styles.input}
        placeholder="Deck Name"
        placeholderTextColor="#fff"
        value={deckName}
        onChangeText={setDeckName}
      />
      <TextInput
        style={styles.input}
        placeholder="Format"
        placeholderTextColor="#fff"
        value={format}
        onChangeText={setFormat}
      />
      <TouchableOpacity style={styles.button} onPress={handleAddDeck}>
        <Text style={styles.buttonText}>Add Deck</Text>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 16,
    backgroundColor: '#2E2E2E',
  },
  input: {
    height: 40,
    width: '80%',
    borderColor: '#474A51',
    borderWidth: 1,
    marginBottom: 12,
    paddingHorizontal: 8,
    borderRadius: 5,
    color: '#fff',
    backgroundColor: '#474A51',
  },
  button: {
    backgroundColor: 'purple',
    padding: 10,
    marginVertical: 5,
    borderRadius: 5,
    alignItems: 'center',
  },
  buttonText: {
    color: '#000',
    fontWeight: 'bold',
  },
});

export default AddDeck;
