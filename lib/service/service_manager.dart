import 'package:flutter_accessibility_service/flutter_accessibility_service.dart';

class BlockPornServiceManager {
  
  // Verifica se o serviço de acessibilidade está habilitado
  static Future<bool> isServiceEnabled() async {
    return await FlutterAccessibilityService.isAccessibilityPermissionEnabled();
  }

  // Abre as configurações de acessibilidade do Android
  static Future<void> requestPermission() async {
    await FlutterAccessibilityService.requestAccessibilityPermission();
  }
}
