package co.lemonlabs.mortar.example;

final class Modules {
  static Object[] list(U2020App app) {
    return new Object[] {
        new U2020Module(app),
        // We do not include DebugU2020Module()
//        new DebugU2020Module()
    };
  }

  private Modules() {
    // No instances.
  }
}
