package org.example.ParkingLot.repositories;

import org.example.ParkingLot.models.Gate;
import org.example.ParkingLot.models.Vehicle;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GateRepository {
    //DB queries
    private Map<Long, Gate> gates = new HashMap<>();

    private Long gateId;

    public Optional<Gate> findGateById(Long id){
        if(gates.containsKey(id)){
            return Optional.of(gates.get(id));
        }
        return Optional.empty();
    }

    public Gate save(Gate gate){
        if(gate.getGateNumber() > 0 && gate.getGateNumber() <= 10) {
            gateId++;
            gate.setId(gateId);
            gates.put(gateId, gate);
            return gate;
        }
        return null;
    }

    public Map<Long, Gate> getGates() {
        return gates;
    }

    public void setGates(Map<Long, Gate> gates) {
        this.gates = gates;
    }
}
