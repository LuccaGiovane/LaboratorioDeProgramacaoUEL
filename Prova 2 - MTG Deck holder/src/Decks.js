import React from 'react';
import { View, Text, StyleSheet, TouchableOpacity, FlatList } from 'react-native';

const Decks = ({ navigation, decks, deleteDeck, editDeck }) => {
  const renderItem = ({ item }) => (
    <View style={styles.deckItemContainer}>
      <TouchableOpacity
        style={styles.deckItem}
        onPress={() => navigation.navigate('DeckDetail', { deck: item })}
      >
        <Text style={styles.deckTitle}>{item.deck}</Text>
        <Text style={styles.deckFormat}>{item.format}</Text>
      </TouchableOpacity>
      <View style={styles.buttonsContainer}>
        <TouchableOpacity style={styles.button} onPress={() => deleteDeck(item.id)}>
          <Text style={styles.buttonText}>Delete</Text>
        </TouchableOpacity>
        <TouchableOpacity
          style={styles.button}
          onPress={() => navigation.navigate('EditDeck', { deck: item, editDeck })}
        >
          <Text style={styles.buttonText}>Edit</Text>
        </TouchableOpacity>
      </View>
    </View>
  );

  return (
    <View style={styles.container}>
      <FlatList
        data={decks}
        renderItem={renderItem}
        keyExtractor={(item) => item.id}
        ListEmptyComponent={<Text style={styles.emptyMessage}>No decks available</Text>}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#2E2E2E',
  },
  deckItemContainer: {
    marginVertical: 8,
    marginHorizontal: 16,
    borderRadius: 10,
    backgroundColor: '#474A51',
    padding: 20,
  },
  deckItem: {
    marginBottom: 10,
  },
  deckTitle: {
    fontSize: 24,
    color: '#fff',
    fontWeight: 'bold',
  },
  deckFormat: {
    fontSize: 15,
    color: '#fff',
  },
  buttonsContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
  },
  button: {
    backgroundColor: '#A9A9A9',
    padding: 10,
    borderRadius: 5,
    alignItems: 'center',
    marginTop: 5,
  },
  buttonText: {
    color: '#000',
    fontWeight: 'bold',
  },
  emptyMessage: {
    textAlign: 'center',
    marginTop: 20,
    color: 'purple',
    fontWeight: 'bold',
    fontSize: 18,
  },
});

export default Decks;
