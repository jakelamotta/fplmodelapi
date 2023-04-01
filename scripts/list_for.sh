mysql -ppassword -uroot -P password <<EOF

use fpl;

select player, (xpabs-0.8687889144630985)/cost as c , team, cost
from calculatedplayerstatistics cps
where cps.position like "F%"
and cps.minutesplayed > 160
and cost < $2
order by c desc
limit $1;

EOF


#and team not in ("Norwich", "Brighton", "Manchester United", "Brentford", "Everton", "Watford", "West H-#am", "Leicester", "Southampton", "Crystal Palace")
