package shop.orm.repository.mapper;

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace;
import com.datastax.oss.driver.api.mapper.annotations.DaoTable;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;
import shop.orm.repository.dao.PurchaseByClientDao;
import shop.orm.repository.dao.PurchaseByProductDao;

@Mapper
public interface PurchaseByProductMapper {

    @DaoFactory
    PurchaseByProductDao purchaseByProductDao(@DaoKeyspace String keyspace, @DaoTable String table);

    @DaoFactory
    PurchaseByProductDao purchaseByProductDao();

}
