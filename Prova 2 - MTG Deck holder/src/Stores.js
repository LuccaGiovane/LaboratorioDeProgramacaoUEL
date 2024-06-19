import React, { useEffect, useState } from 'react';
import { View, Text, StyleSheet } from 'react-native';
import * as Location from 'expo-location';

const stores = [
  { name: 'Piedade Cardhouse', address: 'Rua Alagoas, 888', latitude: -23.3091, longitude: -51.1591 },
  { name: 'Caverna do Basdao', address: 'Rua Senador Souza Naves, 441', latitude: -23.3121, longitude: -51.1621 }
];

const StoresScreen = () => {
  const [sortedStores, setSortedStores] = useState([]);

  useEffect(() => {
    (async () => {
      let { status } = await Location.requestForegroundPermissionsAsync();
      if (status !== 'granted') {
        alert('Permission to access location was denied');
        return;
      }

      let location = await Location.getCurrentPositionAsync({});
      const { latitude, longitude } = location.coords;

      const sortedStores = stores.sort((a, b) => {
        const distanceA = getDistance(latitude, longitude, a.latitude, a.longitude);
        const distanceB = getDistance(latitude, longitude, b.latitude, b.longitude);
        return distanceA - distanceB;
      });

      setSortedStores(sortedStores);
    })();
  }, []);

  const getDistance = (lat1, lon1, lat2, lon2) => {
    const R = 6371e3; 
    const φ1 = lat1 * (Math.PI / 180);
    const φ2 = lat2 * (Math.PI / 180);
    const Δφ = (lat2 - lat1) * (Math.PI / 180);
    const Δλ = (lon2 - lon1) * (Math.PI / 180);

    const a =
      Math.sin(Δφ / 2) * Math.sin(Δφ / 2) +
      Math.cos(φ1) * Math.cos(φ2) * Math.sin(Δλ / 2) * Math.sin(Δλ / 2);
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    const distance = R * c;
    return distance;
  };

  return (
    <View style={styles.container}>
      {sortedStores.map((store, index) => (
        <View key={index} style={styles.storeContainer}>
          <Text style={styles.storeName}>{store.name}</Text>
          <Text style={styles.storeAddress}>{store.address}</Text>
        </View>
      ))}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#2E2E2E',
    padding: 16,
    paddingTop: '15%', 
  },
  storeContainer: {
    marginBottom: 20,
    backgroundColor: '#474A51',
    padding: 10,
    borderRadius: 10,
  },
  storeName: {
    color: 'purple',
    fontWeight: 'bold',
    fontSize: 18,
  },
  storeAddress: {
    color: '#fff',
    fontSize: 16,
  },
});

export default StoresScreen;
