package week4;

import java.util.List;

public class MaintenanceService {
    private final MachineController machineController;

    public MaintenanceService(MachineController machineController) {
        this.machineController = machineController;
    }

    public List<Integer> findMachinesToMaintain() {
        return List.of();
    }
}
