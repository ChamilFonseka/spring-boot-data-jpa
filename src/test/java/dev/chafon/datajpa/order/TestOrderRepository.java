package dev.chafon.datajpa.order;

import dev.chafon.datajpa.BaseRepository;

interface TestOrderRepository extends BaseRepository<Order, Integer> {
    void deleteAll();
}
