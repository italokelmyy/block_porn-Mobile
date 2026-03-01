import 'dart:async';
import 'package:block_porn/service/service_manager.dart';
import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

class BlockPorn extends StatefulWidget {
  const BlockPorn({super.key});

  @override
  State<BlockPorn> createState() => _BlockPornState();
}

class _BlockPornState extends State<BlockPorn> with WidgetsBindingObserver {

  SharedPreferences? _prefs;
  bool switchPorn = false;
  static const String key = "block_porn_enabled";

  @override
  void initState() {
    super.initState();
    _loadSettings();
  }

  Future<void> _loadSettings() async {
    _prefs = await SharedPreferences.getInstance();
    setState(() {
      switchPorn = _prefs?.getBool(key) ?? false;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("BlockPorn", style: TextStyle(color: Colors.white)),
        backgroundColor: Colors.red,
      ),
      backgroundColor: Colors.black,
      body: _configContainer(),
    );
  }

  Widget _configContainer() {
    return Center(
      child: Container(
        height: 300,
        width: 280,
        decoration: BoxDecoration(border: Border.all(color: Colors.red)),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [_switchPorn()],
        ),
      ),
    );
  }

  Widget _switchPorn() {
    return SwitchListTile(
      value: switchPorn,
      onChanged: (value) async {
        setState(() {
          switchPorn = value;
        });

        await _prefs?.setBool(key, value);

        if (value) {
          await BlockPornServiceManager.requestPermission();
        }

      },
      activeThumbColor: Colors.green,
      title: const Text(
        "Ativar Bloqueio",
        style: TextStyle(color: Colors.white),
      ),
    );
  }
}
