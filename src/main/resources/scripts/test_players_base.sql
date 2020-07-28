use fpldata_dev;

select count(*)-1
from Player where name = 'Pierre-Emerick Aubameyang';

select count(*)-3
from SeasonTeamPlayer where player_id =
(select id
 from Player where name = 'Pierre-Emerick Aubameyang');

 select count(*)-6
 from SeasonTeamPlayer where player_id =
 (select id
  from Player where name = 'Olivier Giroud');

   select count(*)-4
   from SeasonTeamPlayer where player_id =
   (select id
    from Player where name = 'Gabriel Fernando de Jesus');

 select count(*)-1
 from SeasonTeamPlayer where player_id =
 (select id
  from Player where name = 'Danny Ward')
  and seasonteam_id in (select id from seasonteam where team_id in (
              select id from Team where name = 'Cardiff')
              and season_id in (select id from season where startyear = 2018)
              );

   select count(*)-2
   from SeasonTeamPlayer where player_id =
   (select id
    from Player where name = 'Mason Greenwood')
    and seasonteam_id in (select id from seasonteam where team_id in (
            select id from Team where name = 'Manchester United'));

       select count(*)-5
       from SeasonTeamPlayer where player_id =
       (select id
        from Player where name = 'Heung-Min Son')
        and seasonteam_id in (select id from seasonteam where team_id in (
                select id from Team where name = 'Tottenham'));

