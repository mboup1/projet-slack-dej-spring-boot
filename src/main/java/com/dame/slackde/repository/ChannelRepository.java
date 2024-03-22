package com.dame.slackde.repository;

import com.dame.slackde.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {}
