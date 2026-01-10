package br.com.shipping_service.entities;

public enum ShippingStatus {

    CREATED {
        @Override
        public ShippingStatus nextStatus() {
            return IN_PREPARATION;
        }
    }, IN_PREPARATION {
        @Override
        public ShippingStatus nextStatus() {
            return READY_FOR_DISPATCH;
        }
    }, READY_FOR_DISPATCH {
        @Override
        public ShippingStatus nextStatus() {
            return IN_TRANSIT;
        }
    }, IN_TRANSIT {
        @Override
        public ShippingStatus nextStatus() {
            return DELIVERED;
        }
    }, DELIVERED {
        @Override
        public ShippingStatus nextStatus() {
            return DELIVERED;
        }
    };

    public abstract ShippingStatus nextStatus();

}
