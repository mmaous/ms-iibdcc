import 'package:flutter/material.dart';

class HomePage extends StatelessWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("TP4 Home Page"),
        backgroundColor: Colors.deepOrange,
      ),
      // This builds the side menu
      drawer: Drawer(
        child: ListView(
          padding: EdgeInsets.zero,
          children: [
            const DrawerHeader(
              decoration: BoxDecoration(color: Colors.deepOrange),
              child: Center(
                child: Text(
                  "Navigation Menu",
                  style: TextStyle(color: Colors.white, fontSize: 24, fontWeight: FontWeight.bold),
                ),
              ),
            ),
            // Reusing the method below to generate menu items
            _buildDrawerItem(
              context: context,
              icon: const Icon(Icons.home, color: Colors.deepOrange),
              itemTitle: "Home",
              route: "/",
            ),
            _buildDrawerItem(
              context: context,
              icon: const Icon(Icons.calculate, color: Colors.deepOrange),
              itemTitle: "Counter",
              route: "/counter",
            ),
          ],
        ),
      ),
      body: const Center(
        child: Text(
          "Welcome to Home!\nSwipe right or click the top-left icon to open the menu.",
          textAlign: TextAlign.center,
          style: TextStyle(fontSize: 18),
        ),
      ),
    );
  }

  // Exact logic from your PDF (Page 63)
  Widget _buildDrawerItem({
    required BuildContext context,
    required Icon icon,
    required String itemTitle,
    required String route,
  }) {
    return ListTile(
      onTap: () {
        Navigator.pop(context); // Closes the drawer first

        // Prevents pushing the same route if we are already on it
        if (ModalRoute.of(context)?.settings.name != route) {
          Navigator.pushNamed(context, route);
        }
      },
      leading: icon,
      trailing: const Icon(Icons.arrow_right, color: Colors.deepOrange),
      title: Text(
        itemTitle,
        style: const TextStyle(fontSize: 18, color: Colors.deepOrange),
      ),
    );
  }
}