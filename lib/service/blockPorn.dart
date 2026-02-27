import 'package:block_porn/service/service_manager.dart';
import 'package:flutter/material.dart';

class BlockPorn extends StatefulWidget {
  const BlockPorn({super.key});

  @override
  State<BlockPorn> createState() => _BlockPornState();
}

class _BlockPornState extends State<BlockPorn> with WidgetsBindingObserver {


  bool switchPorn = false;



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
        child: Column(children: [_switchPorn()],),
      ),
    );
  }
  
  Widget _switchPorn(){
    return SwitchListTile(
      value: switchPorn,
      onChanged: (value) async {
        setState(() {
          switchPorn = value;
        });

        await BlockPornServiceManager.requestPermission();
      },
      activeThumbColor: Colors.green,
      title: const Text(
        "Ativar Bloqueio",
        style: TextStyle(color: Colors.white),
      ),
    );
  }

}
