package shop.orm.repository.mapper;


import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace;
import com.datastax.oss.driver.api.mapper.annotations.DaoTable;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;
import shop.orm.repository.dao.PurchaseByClientDao;

@Mapper
public interface PurchaseByClientMapper {

    @DaoFactory
    PurchaseByClientDao purchaseByClientDao(@DaoKeyspace String keyspace, @DaoTable String table);

    @DaoFactory
    PurchaseByClientDao purchaseByClientDao();

}