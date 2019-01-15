package ai.houyi.zhuque.dao;

import ai.houyi.zhuque.dao.model.Agent;
import ai.houyi.zhuque.dao.model.AgentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AgentMapper {
    long countByExample(AgentExample example);

    int deleteByExample(AgentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Agent record);

    int insertSelective(Agent record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table agent
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    Agent selectOneByExample(AgentExample example);

    List<Agent> selectByExample(AgentExample example);

    Agent selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Agent record, @Param("example") AgentExample example);

    int updateByExample(@Param("record") Agent record, @Param("example") AgentExample example);

    int updateByPrimaryKeySelective(Agent record);

    int updateByPrimaryKey(Agent record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table agent
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsert(@Param("list") List<Agent> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table agent
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsertSelective(@Param("list") List<Agent> list, @Param("selective") Agent.Column ... selective);
}